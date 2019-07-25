package game.base.item.base.constant;

import java.util.HashMap;
import java.util.Map;

import game.base.item.base.effect.ExpEffectProcessor;
import game.base.item.base.effect.IEffectProcessor;

/**
 * @author : ddv
 * @since : 2019/6/26 下午3:27
 */

public enum ItemEffectType {
    /**
     * 经验处理器
     */
    EXP(1, "EXP", ExpEffectProcessor.class),;

    private static Map<String, IEffectProcessor> processors;

    static {
        processors = new HashMap<>();
        ItemEffectType[] values = ItemEffectType.values();
        for (ItemEffectType itemEffectType : values) {
            try {
                processors.put(itemEffectType.getEffectType(), itemEffectType.getEffectProcessor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * typeId
     */
    private int id;
    /**
     * 配置表的type
     */
    private String effectType;
    /**
     * 具体的执行器
     */
    private Class<? extends IEffectProcessor> effectProcessor;

    ItemEffectType(int id, String effectType, Class<? extends IEffectProcessor> effectProcessor) {
        this.id = id;
        this.effectType = effectType;
        this.effectProcessor = effectProcessor;
    }

    // 获取处理器
    public static IEffectProcessor getProcessor(String type) {
        return processors.get(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public Class<? extends IEffectProcessor> getEffectProcessor() {
        return effectProcessor;
    }

    public void setEffectProcessor(Class<? extends IEffectProcessor> effectProcessor) {
        this.effectProcessor = effectProcessor;
    }
}
