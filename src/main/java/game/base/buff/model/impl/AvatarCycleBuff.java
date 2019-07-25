package game.base.buff.model.impl;

import java.util.HashMap;
import java.util.Map;

import game.base.buff.resource.BuffResource;
import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
import game.base.fight.utils.BattleUtil;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;

/**
 * 天神下凡 [属性加成,周期回血]
 *
 * @author : ddv
 * @since : 2019/7/24 6:46 PM
 */

public class AvatarCycleBuff extends BaseCycleBuff {
    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    @Override
    public void init(BuffResource buffResource, BuffContext context) {
        super.init(buffResource, context);
        long hp = context.getParam(BuffContextParamEnum.MAX_HP);
        long mp = context.getParam(BuffContextParamEnum.MAX_MP);

        Attribute hpAttribute = Attribute.valueOf(AttributeType.getByName(BuffContextParamEnum.MAX_HP.name()), hp);
        Attribute mpAttribute = Attribute.valueOf(AttributeType.getByName(BuffContextParamEnum.MAX_MP.name()), mp);
        attributeMap.put(hpAttribute.getAttributeType(), hpAttribute);
        attributeMap.put(mpAttribute.getAttributeType(), mpAttribute);

        this.context.addParam(BuffContextParamEnum.Attribute, attributeMap);
        this.context.addParam(BuffContextParamEnum.Buff_Id, buffId);
        this.context.addParam(BuffContextParamEnum.Attribute_Container, BattleUtil.getUnitAttrComponent(target));
        this.context.addParam(BuffContextParamEnum.CureHp, context.getParam(BuffContextParamEnum.CureHp));
        this.context.addParam(BuffContextParamEnum.CureMp, context.getParam(BuffContextParamEnum.CureHp));
    }

}
