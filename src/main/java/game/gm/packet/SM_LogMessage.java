package game.gm.packet;

/**
 * 服务端打印日志
 *
 * @author : ddv
 * @since : 2019/7/4 下午4:19
 */
public class SM_LogMessage {
    String message;

    public static SM_LogMessage valueOf(String message) {
        SM_LogMessage sm = new SM_LogMessage();
        sm.message = message;
        return sm;
    }

    @Override
    public String toString() {
        return "服务端打印日志" + '\n' + message;
    }
}
