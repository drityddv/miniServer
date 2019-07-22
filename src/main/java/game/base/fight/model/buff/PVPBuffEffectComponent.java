package game.base.fight.model.buff;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.BaseBuffEffect;
import game.base.fight.model.componet.BaseUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.handler.AbstractMapHandler;
import game.world.fight.command.BuffActiveCommand;
import scheduler.constant.JobGroupEnum;
import scheduler.job.common.scene.EffectActiveJob;
import scheduler.job.model.JobEntry;
import spring.SpringContext;

/**
 * pvp buff容器
 *
 * @author : ddv
 * @since : 2019/7/15 10:30 AM
 */

public class PVPBuffEffectComponent extends BaseUnitComponent<BaseCreatureUnit> {

    /**
     * 是否需要修正buff
     */
    private boolean needFix = false;

    private BaseBuffEffect oldBuff;

    /**
     * <JobId,buff> buff存储对象
     */
    private Map<Long, BaseBuffEffect> buffMap = new HashMap<>();
    /**
     * effectTypeId - buff
     */
    private Map<Long, BaseBuffEffect> groupBuffMap = new HashMap<>();

    /**
     * 添加buff 在这里进行合并等操作 相同groupId 相同id合并,不同id按照优先级覆盖 FIXME 修正周期性buff触发器
     *
     * @param buff
     *            需要预处理的buff
     * @return 最终存储的buff
     */
    public BaseBuffEffect handleBuff(BaseBuffEffect buff) {
        long groupId = buff.getEffectTypeId();
        BaseBuffEffect sameGroupBuff = groupBuffMap.get(groupId);

        if (sameGroupBuff != null) {
            if (!buff.canMerge()) {
                // 不能合并的buff
                return null;
            }
            needFix = true;
            oldBuff = sameGroupBuff;
            // 同等级merge 返回旧的buff
            if (buff.getLevel() == sameGroupBuff.getLevel()) {
                sameGroupBuff.merge(buff);
                return sameGroupBuff;
            }
        }
        return buff;
    }

    public void removeBuff(BaseBuffEffect buff) {
        buffMap.remove(buff.getJobId());
        groupBuffMap.remove(buff.getEffectTypeId());
    }

    public void addBuff(BaseBuffEffect buff) {
        BaseBuffEffect finalBuff = handleBuff(buff);
        if (finalBuff == null) {
            return;
        }

        buffMap.put(finalBuff.getJobId(), finalBuff);
        groupBuffMap.put(finalBuff.getEffectTypeId(), finalBuff);

        reviseBuff(finalBuff);
    }

    // 修正或者注册buff
    private void reviseBuff(BaseBuffEffect buffEffect) {
        int mapId = owner.getMapId();
        Map<Long, BaseBuffEffect> buffEffects = AbstractMapHandler.getAbstractMapHandler(mapId).getBuffEffects(mapId);

        JobEntry jobEntry = JobEntry.newRateJob(EffectActiveJob.class,
            (long)buffEffect.getEffectResource().getFrequencyTime(), buffEffect.getRemainCount(), buffEffect.getJobId(),
            JobGroupEnum.BUFF.name(), BuffActiveCommand.valueOf(buffEffect, mapId));
        buffEffects.put(buffEffect.getJobId(), buffEffect);

        if (oldBuff != null) {
            // 删掉旧的 再加新的
            SpringContext.getQuartzService().removeJob(buffEffect.getJobId());
        }

        SpringContext.getQuartzService().addJob(buffEffect.getJobId(), jobEntry);

        needFix = false;
        oldBuff = null;
    }

    /**
     * 是否有同组类型的buff
     *
     * @param groupId
     * @return
     */
    public boolean isExistSameGroupBuff(int groupId) {
        return groupBuffMap.containsKey(groupId);
    }

    /**
     * 获取同组类型buff
     *
     * @param groupId
     * @return
     */
    public BaseBuffEffect getSameGroupBuff(int groupId) {
        return groupBuffMap.get(groupId);
    }

    @Override
    public UnitComponentType getType() {
        return UnitComponentType.BUFF;
    }

    @Override
    public void reset() {
        clear();
    }

    @Override
    public void clear() {
        buffMap.clear();
    }

    public Map<Long, BaseBuffEffect> getBuffMap() {
        return buffMap;
    }
}
