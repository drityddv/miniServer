package game.base.game.attribute.computer;

import java.util.Map;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.AttributeId;
import game.base.object.AbstractCreature;

/**
 * 属性计算器
 *
 * @author : ddv
 * @since : 2019/6/27 上午9:51
 */

public interface IAttributeComputer {

    /**
     * 获取属性计算器对应的属性类型
     *
     * @return
     */
    AttributeType getAttributeType();

    /**
     * 计算属性
     *
     * @param creature
     *            属性拥有者
     * @param accumulateAttrs
     *            初始累加完毕的属性
     * @param attributes
     *            模块属性(未累加)
     * @return 属性值
     */
    long compute(AbstractCreature creature, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes);

    /**
     * 默认计算
     *
     * @param creature
     * @param accumulateAttrs
     * @param attributes
     * @return
     */
    default long computeDefault(AbstractCreature creature, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {
        return compute(creature, accumulateAttrs, attributes);
    }

    /**
     * pvp
     *
     * @param creatureUnit
     * @param accumulateAttrs
     * @param attributes
     * @return
     */
    long computeForPVP(BaseCreatureUnit creatureUnit, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes);

    /**
     * pvp
     *
     * @param creatureUnit
     * @param accumulateAttrs
     * @param attributes
     * @return
     */
    default long computeFinalForPVP(BaseCreatureUnit creatureUnit, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {
        return computeForPVP(creatureUnit, accumulateAttrs, attributes);
    }
}
