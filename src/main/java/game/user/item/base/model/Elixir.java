package game.user.item.base.model;

import game.common.exception.RequestException;
import game.user.player.model.Player;

/**
 *
 * 经验仙丹
 * @author : ddv
 * @since : 2019/6/26 上午10:23
 */

public class Elixir extends AbstractUsableItem {
    @Override
    public void verifyCanUseThrow(Player player, int num) throws RequestException {

    }

    @Override
    public boolean verifyCanUse(Player player, int num) {
        return true;
    }

    @Override
    public void useEffect(Player player, int num) {

    }
}
