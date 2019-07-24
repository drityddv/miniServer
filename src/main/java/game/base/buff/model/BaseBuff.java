package game.base.buff.model;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.resource.BuffResource;
import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
import game.base.effect.model.constant.EffectTypeEnum;
import game.base.effect.model.effect.BaseEffect;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import scheduler.job.model.JobEntry;
import utils.snow.IdUtil;

/**
 * 基础效果[支持合并,过期]
 *
 * @author : ddv
 * @since : 2019/7/15 10:39 AM
 */

public abstract class BaseBuff<T> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseBuff.class);

    protected long configId;

    protected long buffId;

    protected Map<BuffTriggerPoint, List<BaseEffect>> triggerPoints = new HashMap<>();

    // buff调度作业
    protected JobEntry buffJob;
    // buff释放者
    protected BaseCreatureUnit caster;
    // buff作用对象
    protected BaseCreatureUnit target;
    // 当前合并次数
    protected int mergedCount;
    // 效果集合
    protected List<BaseEffect> effectList;
    protected BuffContext context;

    protected BuffResource buffResource;

    public void init(BuffResource buffResource, BuffContext context) {
        for (BuffTriggerPoint triggerPoint : BuffTriggerPoint.values()) {
            triggerPoints.put(triggerPoint, new ArrayList<>());
        }
        this.buffId = IdUtil.getLongId();
        this.mergedCount = 0;
        this.buffResource = buffResource;
        this.effectList = buffResource.getEffectList();
        this.effectList.forEach(baseEffect -> {
            Set<BuffTriggerPoint> triggerPointSet =
                EffectTypeEnum.getByClazz(baseEffect.getClass()).getTriggerPointSet();
            triggerPointSet.forEach(buffTriggerPoint -> {
                triggerPoints.get(buffTriggerPoint).add(baseEffect);
            });
        });

        this.context = BuffContext.valueOf();
        caster = context.getParam(BuffContextParamEnum.CASTER);
        target = context.getParam(BuffContextParamEnum.Target);
        this.context.addParam(BuffContextParamEnum.CASTER, caster);
        this.context.addParam(BuffContextParamEnum.Target, target);
    }

    /**
     * buff合并
     *
     * @param buff
     */
    public void merge(BaseCreatureBuff buff) {

    }

    /**
     * 取消
     */
    public abstract void forceCancel();

    // 初始化调度服务
    public void initBuffJob() {

    }

    public int getLevel() {
        return buffResource.getLevel();
    }

    public boolean canMerge() {
        return buffResource.getMaxMergeCount() != 0;
    }

    public int getGroupId() {
        return buffResource.getGroupId();
    }

    // get set

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public long getBuffId() {
        return buffId;
    }

    public void setBuffId(long buffId) {
        this.buffId = buffId;
    }

    public JobEntry getBuffJob() {
        return buffJob;
    }

    public void setBuffJob(JobEntry buffJob) {
        this.buffJob = buffJob;
    }

    public BaseCreatureUnit getCaster() {
        return caster;
    }

    public void setCaster(BaseCreatureUnit caster) {
        this.caster = caster;
    }

    public BaseCreatureUnit getTarget() {
        return target;
    }

    public void setTarget(BaseCreatureUnit target) {
        this.target = target;
    }

    public int getMergedCount() {
        return mergedCount;
    }

    public void setMergedCount(int mergedCount) {
        this.mergedCount = mergedCount;
    }

    public List<BaseEffect> getEffectList() {
        return effectList;
    }

    public void setEffectList(List<BaseEffect> effectList) {
        this.effectList = effectList;
    }

    public BuffContext getContext() {
        return context;
    }

    public void setContext(BuffContext context) {
        this.context = context;
    }

    public BuffResource getBuffResource() {
        return buffResource;
    }

    public void setBuffResource(BuffResource buffResource) {
        this.buffResource = buffResource;
    }
}
