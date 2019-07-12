package game.base.consume;

/**
 * @author : ddv
 * @since : 2019/7/12 10:02 AM
 */

public class ConsumeParam {
    private long configId;
    private int value;

    public static ConsumeParam valueOf(long configId, int value) {
        ConsumeParam param = new ConsumeParam();
        param.configId = configId;
        param.value = value;
        return param;
    }

    public static ConsumeParam valueOf(int value) {
        ConsumeParam param = new ConsumeParam();
        param.value = value;
        return param;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
