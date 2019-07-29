package game.base.effect.model.impl;

import java.util.ArrayList;
import java.util.Map;

import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.effect.model.BaseEffect;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.PvpBuffAttributeId;

/**
 * @author : ddv
 * @since : 2019/7/24 9:13 PM
 */

public class PlusBuffAttrEffect extends BaseEffect {

    @Override
    public void active(BuffContext buffContext) {
        plusAttribute(buffContext);
    }

    private void plusAttribute(BuffContext buffContext) {
        PVPCreatureAttributeComponent attributeComponent = buffContext.getParam(BuffParamEnum.Attribute_Container);
        Map<AttributeType, Attribute> attributeMap = buffContext.getParam(BuffParamEnum.Attribute);
        PvpBuffAttributeId attributeId = PvpBuffAttributeId.valueOf(buffContext.getParam(BuffParamEnum.Buff_Id));
        attributeComponent.putAttributesWithRecompute(attributeId, new ArrayList<>(attributeMap.values()), false);
    }
}
