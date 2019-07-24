package game.base.fight.model.buff;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.base.fight.model.componet.BaseUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

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

    private BaseCreatureBuff oldBuff;

    /**
     * <JobId,buff> buff存储对象
     */
    private Map<Long, BaseCreatureBuff> buffMap = new HashMap<>();
    /**
     * effectTypeId - buff
     */
    private Map<Long, BaseCreatureBuff> groupBuffMap = new HashMap<>();

    /**
     * 添加buff 在这里进行合并等操作 相同groupId 相同id合并,不同id按照优先级覆盖 FIXME 修正周期性buff触发器
     *
     * @param buff
     *            需要预处理的buff
     * @return 最终存储的buff
     */
    public BaseCreatureBuff handleBuff(BaseCreatureBuff buff) {
//        long groupId = buff.getEffectTypeId();
//        BaseCreatureBuff sameGroupBuff = groupBuffMap.get(groupId);
//
//        if (sameGroupBuff != null) {
//            if (!buff.canMerge()) {
//                // 不能合并的buff
//                return null;
//            }
//            needFix = true;
//            oldBuff = sameGroupBuff;
//            // 同等级merge 返回旧的buff
//            if (buff.getLevel() == sameGroupBuff.getLevel()) {
//                sameGroupBuff.merge(buff);
//                return sameGroupBuff;
//            }
//        }
        return buff;
    }

    public void removeBuff(BaseCreatureBuff buff) {
//        buffMap.remove(buff.getJobId());
//        groupBuffMap.remove(buff.getEffectTypeId());
    }

    // 只是加buff 不进行调度
    public void justAddBuff(BaseCreatureBuff buffEffect) {
        BaseCreatureBuff finalBuff = handleBuff(buffEffect);
        if (finalBuff == null) {
            return;
        }

//        buffMap.put(finalBuff.getJobId(), finalBuff);
//        groupBuffMap.put(finalBuff.getEffectTypeId(), finalBuff);
    }

    public void addBuff(BaseCreatureBuff buff) {
        BaseCreatureBuff finalBuff = handleBuff(buff);
        if (finalBuff == null) {
            return;
        }

//        buffMap.put(finalBuff.getJobId(), finalBuff);
//        groupBuffMap.put(finalBuff.getEffectTypeId(), finalBuff);

        reviseBuff(finalBuff);
    }

    // 修正或者注册buff
    private void reviseBuff(BaseCreatureBuff buffEffect) {
        // int mapId = owner.getMapId();
        // Map<Long, BaseCreatureBuff> buffEffects =
        // AbstractMapHandler.getAbstractMapHandler(mapId).getBuffEffects(mapId);
        //
        // JobEntry jobEntry = JobEntry.newRateJob(EffectActiveJob.class,
        // (long)buffEffect.getEffectResource().getFrequencyTime(), buffEffect.getRemainCount(), buffEffect.getJobId(),
        // JobGroupEnum.BUFF.name(), BuffActiveCommand.valueOf(buffEffect, mapId));
        // buffEffects.put(buffEffect.getJobId(), buffEffect);
        //
        // if (oldBuff != null) {
        // // 删掉旧的 再加新的
        // SpringContext.getQuartzService().removeJob(buffEffect.getJobId());
        // }
        //
        // SpringContext.getQuartzService().addJob(buffEffect.getJobId(), jobEntry);
        //
        // needFix = false;
        // oldBuff = null;
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
    public BaseCreatureBuff getSameGroupBuff(int groupId) {
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

    public Map<Long, BaseCreatureBuff> getBuffMap() {
        return buffMap;
    }
}
