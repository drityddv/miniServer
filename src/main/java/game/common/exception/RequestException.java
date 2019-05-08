package game.common.exception;

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

    public int getErrorCode() {
        return errorCode;
    }

}
