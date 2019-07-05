package game.base.game.attribute.computer.impl;

import java.util.Map;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.computer.AbstractAttributeComputer;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.util.AttributeUtils;
import game.base.object.AbstractCreature;

/**
 * @author : ddv
 * @since : 2019/6/27 上午10:51
 */

public class OriginalAttributeComputer extends AbstractAttributeComputer {

    public OriginalAttributeComputer(AttributeType type) {
        super(type);
    }

    @Override
    public long compute(AbstractCreature creature, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {
        return AttributeUtils.getRealAttributeValue(accumulateAttrs, getAttributeType());
    }

    @Override
    public long computeForPVP(BaseCreatureUnit creatureUnit, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {
        return AttributeUtils.getRealAttributeValue(accumulateAttrs, getAttributeType());
    }
}
