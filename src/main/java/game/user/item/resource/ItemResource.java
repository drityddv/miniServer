package game.user.item.resource;

import game.user.item.base.constant.ItemType;
import middleware.anno.Init;
import middleware.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/5/31 下午5:25
 */
@MiniResource
public class ItemResource {
    // 物品id
    private long configId;
    // 物品名称
    private String itemName;
    // 物品等级 1 - 10 递增成长
    private int itemLevel;
    // 最大堆叠数 0为无限制 1不可堆叠
    private int overLimit;
    // 物品类型
    private String typeString;
    private ItemType itemType;
    // 道具效果
    private String effectString;
    // 属性信息
    private String attributeString;

    @Init
    private void init() {
        itemType = ItemType.getTypeByName(typeString);
    }

    // get and set
    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getEffectString() {
        return effectString;
    }

    public void setEffectString(String effectString) {
        this.effectString = effectString;
    }

    public String getAttributeString() {
        return attributeString;
    }

    public void setAttributeString(String attributeString) {
        this.attributeString = attributeString;
    }

    public int getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    @Override
    public String toString() {
        return "ItemResource{" + "configId=" + configId + ", itemName='" + itemName + '\'' + ", effectString='"
            + effectString + '\'' + '}';
    }
}
