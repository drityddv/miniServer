package client.handler.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.handler.IHandler;
import game.user.login.packet.SM_LoginSuccess;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:18
 */

public class LoginHandler implements IHandler<SM_LoginSuccess> {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handler(SM_LoginSuccess sm) {
        logger.info("登陆成功!");
    }
}
