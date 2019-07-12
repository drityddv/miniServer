package game.base.consume;

/**
 * @author : ddv
 * @since : 2019/7/12 9:58 AM
 */

public class VerifyResult {
    private boolean success;
    private int code;

    public static VerifyResult success() {
        VerifyResult result = new VerifyResult();
        result.success = true;
        return result;
    }

    public static VerifyResult failed(int code) {
        VerifyResult result = new VerifyResult();
        result.success = false;
        result.code = code;
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
