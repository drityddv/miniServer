package game.base.buff.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.resource.BuffResource;
import game.base.effect.model.BaseEffect;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import quartz.job.model.JobEntry;
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

    protected Map<BuffTriggerPointEnum, List<BaseEffect>> triggerPoints;

    // buff 周期调度
    protected JobEntry scheduleJob;
    // buff 结束调度
    protected JobEntry cancelJob;
    // buff释放者
    protected BaseCreatureUnit caster;
    // buff作用对象
    protected BaseCreatureUnit target;
    // 当前合并次数
    protected int mergedCount;

    // 效果集合
    protected BuffContext context;

    protected BuffResource buffResource;

    public void init(BuffResource buffResource, BuffContext context) {

        this.buffId = IdUtil.getLongId();
        this.configId = buffResource.getConfigId();
        this.mergedCount = 0;
        this.buffResource = buffResource;
        this.triggerPoints = buffResource.getTriggerPoints();

        this.context = context;
        this.caster = context.getParam(BuffParamEnum.CASTER);
        this.target = context.getParam(BuffParamEnum.Target);

        doInit();
    }

    protected void doInit() {

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

    // buff是否需要调度取消
    public boolean isNeedCancel() {
        return buffResource.getDurationTime() > 0;
    }

    // buff是否需要周期调度
    public boolean isNeedSchedule() {
        return buffResource.getFrequencyTime() > 0;
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

    public JobEntry getScheduleJob() {
        return scheduleJob;
    }

    public void setScheduleJob(JobEntry scheduleJob) {
        this.scheduleJob = scheduleJob;
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
