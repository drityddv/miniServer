package game.rpc.service;

import game.role.player.model.Player;
import micro.constant.MicroServiceEnum;

/**
 * @author : ddv
 * @since : 2019/12/13 2:31 PM
 */

public interface IRpcService {

    /**
     * 远程调用rpc
     */
    void remoteCall(Player player, MicroServiceEnum serviceEnum, Object requestPacket);

	/**
	 * 初始化
	 */
	void init();
}
