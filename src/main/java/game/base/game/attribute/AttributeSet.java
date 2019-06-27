package game.base.game.attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/6/26 下午5:23
 */

public class AttributeSet {
    /**
     * 模块战力
     */
    private long battleScore;

    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

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
