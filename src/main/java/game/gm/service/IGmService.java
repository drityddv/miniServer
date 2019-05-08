package game.gm.service;

import game.gm.packet.CM_GmCommand;
import net.model.USession;

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
     * @param session
     * @param request
     */
    void invoke(USession session, CM_GmCommand request);
}
