package game.user.item.base.model;

import game.base.message.exception.RequestException;
import game.role.player.model.Player;

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
        return true;
    }

    @Override
    public void useEffect(Player player, int num) {

    }

    @Override
    public String toString() {
        return "Stone{" + "configId=" + configId + ", num=" + num + '}';
    }
}
