package game.base.game.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/6/26 下午5:23
 */

public class AttributeSet {
    /**
     * 模块战力 等排行榜用
     */
    private long battleScore;

    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    public static AttributeSet valueOf(List<Attribute> attributeList) {
        AttributeSet attributeSet = new AttributeSet();
        for (Attribute attribute : attributeList) {
            attributeSet.attributeMap.put(attribute.getAttributeType(), attribute);
            attributeSet.plusBattleScore(attribute.getValue());
        }
        return attributeSet;
    }

    public void plusBattleScore(long value) {
        battleScore += value;
    }

    /**
     * 获取属性
     *
     * @param type
     * @return
     */
    public long getAttribute(AttributeType type) {
        Attribute attribute = attributeMap.get(type);
        return attribute == null ? 0L : attribute.getValue();
    }

    // get and set
    public long getBattleScore() {
        return battleScore;
    }

    public void setBattleScore(long battleScore) {
        this.battleScore = battleScore;
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
