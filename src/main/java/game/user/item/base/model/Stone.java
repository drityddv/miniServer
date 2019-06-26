package game.user.item.base.model;

import game.common.exception.RequestException;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/25 下午6:28
 */

public class Stone extends AbstractUsableItem {
    @Override
    public void verifyCanUseThrow(Player player, int num) throws RequestException {

    }

    @Override
    public boolean verifyCanUse(Player player, int num) {
        return false;
    }

    @Override
    public void useEffect(Player player, int num) {

    }
}
