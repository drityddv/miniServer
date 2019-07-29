package game.base.buff.model.impl;

import java.util.HashMap;
import java.util.Map;

import game.base.buff.model.BuffParamEnum;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;

/**
 * 天神下凡 [属性加成,周期回血]
 *
 * @author : ddv
 * @since : 2019/7/24 6:46 PM
 */

public class AvatarBuff extends BaseScheduleBuff {
    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    @Override
    protected void doInit() {
        long hp = buffResource.getBuffConfig().getParam(BuffParamEnum.MAX_HP);
        long mp = buffResource.getBuffConfig().getParam(BuffParamEnum.MAX_HP);

        Attribute hpAttribute = Attribute.valueOf(AttributeType.getByName(BuffParamEnum.MAX_HP.name()), hp);
        Attribute mpAttribute = Attribute.valueOf(AttributeType.getByName(BuffParamEnum.MAX_MP.name()), mp);
        attributeMap.put(hpAttribute.getAttributeType(), hpAttribute);
        attributeMap.put(mpAttribute.getAttributeType(), mpAttribute);

        this.context.addParam(BuffParamEnum.Attribute, attributeMap);
        this.context.addParam(BuffParamEnum.Buff_Id, buffId);
        this.context.addParam(BuffParamEnum.Attribute_Container, target.getAttributeComponent());
        this.context.addParam(BuffParamEnum.CureHp, context.getParam(BuffParamEnum.CureHp));
        this.context.addParam(BuffParamEnum.CureMp, context.getParam(BuffParamEnum.CureHp));
    }
}
