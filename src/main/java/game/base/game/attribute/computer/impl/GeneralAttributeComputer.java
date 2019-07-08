package game.base.game.attribute.computer.impl;

import java.util.Map;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.IExtendAttribute;
import game.base.game.attribute.computer.AbstractAttributeComputer;
import game.base.game.attribute.constant.GlobalConst;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.util.AttributeUtils;
import game.base.object.AbstractCreature;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/27 上午10:11
 */

public class GeneralAttributeComputer extends AbstractAttributeComputer {

    public GeneralAttributeComputer(AttributeType type) {
        super(type);
    }

    @Override
    public long compute(AbstractCreature creature, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {

        // 计算关联的属性
        long baseValue = AttributeUtils.getCalculateAttributeValue(accumulateAttrs, getAttributeType());

        // 目前只有玩家属性计算 但是还是占个位
        if (creature instanceof Player) {
            Player player = (Player)creature;
            // 额外计算其他模块带来的额外属性[例如传世,雷霆等]
            baseValue += IExtendAttribute.computeAddValue(player, accumulateAttrs, getAttributeType());
        }

        // 计算基础属性的百分比加成
        long baseRate = AttributeUtils.getCalculateAttributeRate(accumulateAttrs, getAttributeType());
        return (long)Math.max(0, (baseValue * (1 + GlobalConst.getRatio(baseRate))));
    }

    // 这里先把pvp和player的计算方式设为一样
    @Override
    public long computeForPVP(BaseCreatureUnit creatureUnit, Map<AttributeType, Attribute> accumulateAttrs,
        Map<AttributeId, AttributeSet> attributes) {
        // 计算关联的属性
        long baseValue = AttributeUtils.getCalculateAttributeValue(accumulateAttrs, getAttributeType());

        long baseRate = AttributeUtils.getCalculateAttributeRate(accumulateAttrs, getAttributeType());
        return (long)Math.max(0, (baseValue * (1 + GlobalConst.getRatio(baseRate))));
    }
}
