package game.base.effect.model.effect;

import java.util.ArrayList;
import java.util.Map;

import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
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
        PVPCreatureAttributeComponent attributeComponent =
            buffContext.getParam(BuffContextParamEnum.Attribute_Container);
        Map<AttributeType, Attribute> attributeMap = buffContext.getParam(BuffContextParamEnum.Attribute);
        PvpBuffAttributeId attributeId = PvpBuffAttributeId.valueOf(buffContext.getParam(BuffContextParamEnum.Buff_Id));
        attributeComponent.putAttributesWithRecompute(attributeId, new ArrayList<>(attributeMap.values()), false);
    }
}
