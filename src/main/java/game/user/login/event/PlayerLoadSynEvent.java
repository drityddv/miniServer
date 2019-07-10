package game.user.login.event;

import ebus.model.IEvent;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/22 下午10:14
 */

public class PlayerLoadSynEvent implements IEvent {

    private Player player;

    public static PlayerLoadSynEvent valueOf(Player player) {
        PlayerLoadSynEvent event = new PlayerLoadSynEvent();
        event.player = player;
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PlayerLoadSynEvent{" + "player=" + player + '}';
    }
}
