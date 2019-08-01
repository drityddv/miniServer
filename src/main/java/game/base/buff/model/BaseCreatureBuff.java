package game.base.buff.model;

import game.base.buff.resource.BuffResource;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import spring.SpringContext;

/**
 * 持续类buff
 *
 * @author : ddv
 * @since : 2019/7/15 12:01 PM
 */

public abstract class BaseCreatureBuff extends BaseBuff<BaseCreatureUnit> {
    protected int mapId;
    protected long sceneId;
    /**
     * 是否需要提交调度请求
     */
    protected volatile boolean needScheduled = false;
    /**
     * 是否需要提交取消buff调度请求
     */
    protected volatile boolean needScheduleCancel = false;

    /**
     * 注册调度
     */
    public void schedule() {
        if (needScheduled) {
            SpringContext.getQuartzService().scheduleJob(scheduleJob);
            needScheduled = false;
        }

        if (needScheduleCancel) {
            SpringContext.getQuartzService().scheduleJob(cancelJob);
            needScheduleCancel = false;
        }
    }

    @Override
    public void init(BuffResource buffResource, BuffContext context) {
        super.init(buffResource, context);
        mapId = caster.getMapId();
        sceneId = caster.getSceneId();
    }

    /**
     * 修正调度作业
     */
    public void flushCycleJob() {
        cancelSchedule();
        initBuffJob();
        schedule();
    }

    public void cancelSchedule() {

    }

    /**
     * 外界第一次启动buff的入口
     */
    public boolean buffActive() {
        boolean success = registerTarget();
        if (success) {
            registerCaster();
            triggerBuff(BuffTriggerPointEnum.First_Active);
        }
        return success;
    }

    public void triggerBuff(BuffTriggerPointEnum point) {
        triggerPoints.get(point).forEach(baseEffect -> {
            baseEffect.active(context);
        });
    }

    /**
     * 施法者注册buff
     */
    public void registerCaster() {
        PVPBuffComponent buffComponent = caster.getBuffComponent();
        buffComponent.addCastBuff(this);
    }

    /**
     * 施法者卸载
     */
    public void releaseCaster() {
        caster.getBuffComponent().removeCastBuff(this);
    }

    /**
     * 拥有者注册
     */
    public boolean registerTarget() {
        return target.getBuffComponent().addBuff(this);
    }

    /**
     * 拥有者卸载
     */
    public void releaseTarget() {
        target.getBuffComponent().removeBuff(this);
    }

    public boolean isScheduleBuff() {
        return false;
    }

    /**
     * 尝试关闭
     */
    public abstract void tryCancel();
}
