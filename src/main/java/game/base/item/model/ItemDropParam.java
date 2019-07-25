package game.base.item.model;

/**
 * 物品掉落配置
 *
 * @author : ddv
 * @since : 2019/7/25 3:52 PM
 */

public class ItemDropParam {
    private long configId;
    private long itemId;
    private int num;
    private double radio;

    public ItemDropParam() {}

    public ItemDropParam(long configId, long itemId, int num, double radio) {
        this.configId = configId;
        this.itemId = itemId;
        this.num = num;
        this.radio = radio;
    }

    public static ItemDropParam valueOf(long configId, long itemId, int num, double radio) {
        ItemDropParam dropParam = new ItemDropParam(configId, itemId, num, radio);
        return dropParam;
    }

    public long getConfigId() {
        return configId;
    }

    public long getItemId() {
        return itemId;
    }

    public int getNum() {
        return num;
    }

    public double getRadio() {
        return radio;
    }
}
