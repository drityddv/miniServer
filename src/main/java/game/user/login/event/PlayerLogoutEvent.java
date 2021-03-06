package game.user.login.event;

import ebus.model.IEvent;
import game.role.player.model.Player;

/**
 * 用户登出事件
 *
 * @author : ddv
 * @since : 2019/7/8 下午4:04
 */

public class PlayerLogoutEvent implements IEvent {

    private Player player;

    public static PlayerLogoutEvent valueOf(Player player) {
        PlayerLogoutEvent event = new PlayerLogoutEvent();
        event.player = player;
        return event;
    }
}
