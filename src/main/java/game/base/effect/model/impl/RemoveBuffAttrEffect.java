package game.base.effect.model.impl;

import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.effect.model.BaseEffect;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.game.attribute.id.PvpBuffAttributeId;

/**
 * @author : ddv
 * @since : 2019/7/25 11:14 AM
 */

public class RemoveBuffAttrEffect extends BaseEffect {

    @Override
    public void active(BuffContext buffContext) {
        removeAttribute(buffContext);
    }

    private void removeAttribute(BuffContext buffContext) {
        PVPCreatureAttributeComponent attributeComponent = buffContext.getParam(BuffParamEnum.Attribute_Container);
        PvpBuffAttributeId attributeId = PvpBuffAttributeId.valueOf(buffContext.getParam(BuffParamEnum.Buff_Id));
        attributeComponent.removeAttributeModel(attributeId);
    }
}
