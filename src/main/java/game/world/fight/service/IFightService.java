package game.world.fight.service;

import java.util.List;

import game.role.player.model.Player;

/**
 * 战斗服务
 *
 * @author : ddv
 * @since : 2019/7/5 下午9:26
 */

public interface IFightService {

    /**
     * 查看场景对象信息
     *
     * @param player
     * @param unitId
     */
    void logUnitBattleInfo(Player player, long unitId);

    /**
     * 使用单点指向性技能
     *
     * @param player
     * @param skillId
     * @param targetId
     */
    void useSinglePointSkill(Player player, long skillId, long targetId);

    /**
     * 使用群体指向性技能
     *
     * @param player
     * @param skillId
     * @param targetIdList
     */
    void useGroupPointSkill(Player player, long skillId, List<Long> targetIdList);

    /**
     * 使用aoe技能
     *
     * @param player
     * @param skillId
     * @param centerX
     * @param centerY
     * @param centerType
     */
    void useAoeSkill(Player player, long skillId, int centerX, int centerY, int centerType);

    /**
     * 使用对自己释放的技能
     *
     * @param player
     * @param skillId
     */
    void useSelfSkill(Player player, long skillId);
}
