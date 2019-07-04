package game.gm.service;

import game.gm.packet.CM_GmCommand;
import game.user.player.model.Player;

/**
 * gm命令
 *
 * @author : ddv
 * @since : 2019/5/7 下午2:49
 */

public interface IGmService {

    /**
     * 反射调用
     *
     * @param player
     * @param request
     */
    void invoke(Player player, CM_GmCommand request);
}
