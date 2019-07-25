package game.base.item.base.effect;

import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/26 下午3:20
 */

public interface IEffectProcessor {

    /**
     * 道具生效处理器
     *
     * @param player
     * @param exp
     */
    void invokeItemEffect(Player player, long exp);
}
