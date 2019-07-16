package game.world.fight.service;

import game.base.fight.model.pvpunit.FighterAccount;
import game.role.player.model.Player;

/**
 * 战斗服务
 *
 * @author : ddv
 * @since : 2019/7/5 下午9:26
 */

public interface IFightService {

    /**
     * 为玩家初始化战斗信息
     *
     * @param player
     * @return
     */
    FighterAccount initForPlayer(Player player);

    /**
     * 使用单点指向性技能
     *
     * @param player
     * @param skillId
     * @param targetId
     */
    void useSinglePointSkill(Player player, long skillId, long targetId);
}
