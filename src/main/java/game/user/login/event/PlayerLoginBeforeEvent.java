package game.user.login.event;

import game.base.ebus.IEvent;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/22 下午10:14
 */

public class PlayerLoginBeforeEvent implements IEvent {

    private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
