package game.base.consume;

/**
 * @author : ddv
 * @since : 2019/7/12 9:58 AM
 */

public class VerifyResult {
    private boolean success;
    private int code;

    public VerifyResult() {
        success = true;
    }

    public static VerifyResult success() {
        VerifyResult result = new VerifyResult();
        result.success = true;
        return result;
    }

    public void failed(int code) {
        this.success = false;
        this.code = code;
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
