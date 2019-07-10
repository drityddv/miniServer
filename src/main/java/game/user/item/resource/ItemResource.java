package game.user.item.resource;

import java.util.HashMap;
import java.util.Map;

import game.user.item.base.constant.ItemType;
import resource.anno.Init;
import resource.anno.MiniResource;
import utils.JodaUtil;

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
    // 道具效果配置
    private String effectString;
    private Map<String, Long> effectParam;

    @Init
    private void init() {
        analysisEffect();
        analysisItemType();
    }

    private void analysisItemType() {
        itemType = ItemType.getTypeByName(typeString);
    }

    private void analysisEffect() {
        if (effectString != null && !effectString.equals("")) {
            String[] split = effectString.split(",");
            effectParam = new HashMap<>();
            for (String singleString : split) {
                String[] strings = singleString.split(":");
                effectParam.put(strings[0], JodaUtil.convertFromString(Long.class, strings[1]));
            }
        }
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

    public int getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    public Map<String, Long> getEffectParam() {
        return effectParam;
    }

    public void setEffectParam(Map<String, Long> effectParam) {
        this.effectParam = effectParam;
    }

    @Override
    public String toString() {
        return "ItemResource{" + "configId=" + configId + ", itemName='" + itemName + '\'' + ", effectString='"
            + effectString + '\'' + '}';
    }
}
