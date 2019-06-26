package game.user.item.base.effect;

import game.user.player.model.Player;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/26 下午3:25
 */

public class ExpEffectProcessor implements IEffectProcessor {

    @Override
    public void invokeItemEffect(Player player, long exp) {
        SpringContext.getPlayerService().addException(player, exp);
    }
}
