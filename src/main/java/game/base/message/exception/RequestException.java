package game.base.message.exception;

import client.MessageEnum;

/**
 * @author : ddv
 * @since : 2019/5/6 下午2:20
 */

public class RequestException extends RuntimeException {

    private int errorCode;

    public static void throwException(int i18nId) {
        RequestException exception = new RequestException();
        exception.errorCode = i18nId;
        throw exception;
    }

    public static void throwException(MessageEnum messageEnum) {
        RequestException exception = new RequestException();
        exception.errorCode = messageEnum.getId();
        throw exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
