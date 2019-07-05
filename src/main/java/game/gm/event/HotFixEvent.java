package game.gm.event;

import game.base.ebus.IEvent;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/5 下午3:38
 */

public class HotFixEvent implements IEvent {

    private Player player;
    private String resourceName;

    public static HotFixEvent valueOf(Player player, String resourceName) {
        HotFixEvent event = new HotFixEvent();
        event.player = player;
        event.resourceName = resourceName;
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public String getResourceName() {
        return resourceName;
    }
}
