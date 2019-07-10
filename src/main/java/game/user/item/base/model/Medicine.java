package game.user.item.base.model;

import game.base.message.exception.RequestException;
import game.role.player.model.Player;

/**
 * 药水
 *
 * @author : ddv
 * @since : 2019/6/25 下午4:38
 */

public class Medicine extends AbstractUsableItem {

    @Override
    public void verifyCanUseThrow(Player player, int num) throws RequestException {

    }

    // 药水使用无限制
    @Override
    public boolean verifyCanUse(Player player, int num) {
        return true;
    }

    @Override
    public void useEffect(Player player, int num) {
        // 等加入战斗容器再做
    }
}
