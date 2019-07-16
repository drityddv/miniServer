package game.base.fight.model.buff;

import java.util.HashMap;
import java.util.Map;

import game.base.effect.model.BaseBuffEffect;
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
     * <effectConfigId,buff> buff存储对象
     */
    private Map<Long, BaseBuffEffect> buffMap = new HashMap<>();

    private Map<Integer, BaseBuffEffect> groupBuffMap = new HashMap<>();

    // private Map<EffectType, List<BaseBuffEffect>> typeBuffMap = new HashMap<>();

    /**
     * 添加buff 在这里进行合并等操作 相同groupId 相同id合并,不同id按照优先级覆盖
     *
     * @param buff
     *            需要预处理的buff
     * @return 最终存储的buff
     */
    public BaseBuffEffect doAddBuff(BaseBuffEffect buff) {
        int groupId = buff.getEffectResource().getGroupId();
        BaseBuffEffect sameGroupBuff = groupBuffMap.get(groupId);

        if (sameGroupBuff != null) {
            if (!buff.canMerge()) {
                // 不能合并的buff
                return null;
            }

            // 同等级merge
            if (buff.getLevel() == sameGroupBuff.getLevel()) {
                sameGroupBuff.merge(buff);
                return sameGroupBuff;
            } else {
                // 等级高覆盖等级低的 如果新buff等级低就不允许合并
                if (buff.getLevel() > sameGroupBuff.getLevel()) {
                    cover(sameGroupBuff, buff);
                    return buff;
                }
            }

        }
        return buff;
    }

    /**
     * buff覆盖 后者覆盖前者
     *
     * @param buff1
     * @param buff2
     */
    private void cover(BaseBuffEffect buff1, BaseBuffEffect buff2) {
        removeBuff(buff1);
        addBuff(buff2);
    }

    private void removeBuff(BaseBuffEffect buff) {
        buffMap.remove(buff.getConfigId());
        groupBuffMap.remove(buff.getGroupId());
    }

    private void addBuff(BaseBuffEffect buff) {
        buffMap.put(buff.getConfigId(), buff);
        groupBuffMap.put(buff.getGroupId(), buff);
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
