package game.base.fight.model.buff;

import java.util.HashMap;
import java.util.Map;

import game.base.buff.model.BaseCreatureBuff;
import game.base.fight.model.componet.BaseUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * pvp buff容器
 *
 * @author : ddv
 * @since : 2019/7/15 10:30 AM
 */

public class PVPBuffComponent extends BaseUnitComponent<BaseCreatureUnit> {

    /**
     * 是否需要修正buff
     */
    private boolean needFix = false;
    /**
     * 是否新的覆盖旧的
     */
    private boolean covered = false;

    private BaseCreatureBuff oldBuff;
    /**
     * <buffId,buff>释放的buff
     */
    private Map<Long, BaseCreatureBuff> castBuffMap = new HashMap<>();

    /**
     * <buffId,buff> buff存储对象
     */
    private Map<Long, BaseCreatureBuff> buffMap = new HashMap<>();
    /**
     * groupId - buff
     */
    private Map<Integer, BaseCreatureBuff> groupBuffMap = new HashMap<>();

    /**
     * 添加buff
     *
     * @param buff
     *            需要预处理的buff
     * @return 最终存储的buff
     */
    public BaseCreatureBuff handleBuff(BaseCreatureBuff buff) {
        int groupId = buff.getGroupId();
        BaseCreatureBuff sameGroupBuff = groupBuffMap.get(groupId);

        if (sameGroupBuff != null) {
            if (!buff.canMerge()) {
                // 不能合并
                return null;
            }
            oldBuff = sameGroupBuff;
            // 新buff等级低合并 等级高就覆盖
            if (buff.getLevel() <= sameGroupBuff.getLevel()) {
                sameGroupBuff.merge(buff);
                needFix = true;
                return sameGroupBuff;
            } else {

                covered = true;
                return buff;
            }
        }
        return buff;
    }

    public void removeBuff(BaseCreatureBuff buff) {
        buffMap.remove(buff.getBuffId());
        groupBuffMap.remove(buff.getGroupId());
    }

    /**
     *
     * @param buff
     * @return true表示这次添加的buff入驻容器
     */
    public boolean addBuff(BaseCreatureBuff buff) {
        BaseCreatureBuff finalBuff = handleBuff(buff);
        if (finalBuff == null) {
            return false;
        }

        if (covered) {
            oldBuff.forceCancel();
            covered = false;
            return true;
        }

        if (needFix) {
            oldBuff.flushCycleJob();
            needFix = false;
            return false;
        }

        buffMap.put(finalBuff.getBuffId(), finalBuff);
        groupBuffMap.put(finalBuff.getGroupId(), finalBuff);
        return true;

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

    /**
     * 注册施放的buff
     *
     * @param buff
     */
    public void addCastBuff(BaseCreatureBuff buff) {
        castBuffMap.put(buff.getBuffId(), buff);
    }

    public void removeCastBuff(BaseCreatureBuff buff) {
        castBuffMap.remove(buff.getBuffId());
    }

    public Map<Long, BaseCreatureBuff> getCastBuffMap() {
        return castBuffMap;
    }

    public Map<Integer, BaseCreatureBuff> getGroupBuffMap() {
        return groupBuffMap;
    }
}
