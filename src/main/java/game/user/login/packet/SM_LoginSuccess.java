package game.user.login.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;

/**
 *
 * @author : ddv
 * @since : 2019/4/29 下午3:28
 */

public class SM_LoginSuccess {

    private static final Logger logger = LoggerFactory.getLogger("client");

    public static SM_LoginSuccess valueOf() {
        return new SM_LoginSuccess();
    }

    @Action
    private void action() {
        logger.info("登陆成功!");
    }
}
