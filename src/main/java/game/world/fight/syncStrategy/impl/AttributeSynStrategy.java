package game.world.fight.syncStrategy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.LockAttribute;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.role.player.model.Player;
import game.world.fight.syncStrategy.BasePlayerSyncStrategy;

/**
 * @author : ddv
 * @since : 2019/7/22 11:18 AM
 */

public class AttributeSynStrategy extends BasePlayerSyncStrategy {

    private Map<AttributeType, Attribute> finalAttrs = new HashMap<>();

    private Map<AttributeId, AttributeSet> modeAttributeSet = new HashMap<>();

    public static AttributeSynStrategy valueOf(Player player) {
        AttributeSynStrategy synStrategy = new AttributeSynStrategy();
        PlayerAttributeContainer attributeContainer = player.getAttributeContainer();

        attributeContainer.getFinalAttributes().values().stream().forEach(attribute -> {
            synStrategy.finalAttrs.put(attribute.getAttributeType(), LockAttribute.wrapper(attribute));
        });

        attributeContainer.getModelAttributeSet().forEach((attributeId, attributeSet) -> {
            synStrategy.modeAttributeSet.put(attributeId,
                AttributeSet.valueOf(new ArrayList<>(attributeSet.getAttributeMap().values())));
        });

        return synStrategy;

    }

    // 百分比修正其他属性
    @Override
    public void syncInfo(FighterAccount fighterAccount) {
        PlayerUnit playerUnit = getPlayerUnit(fighterAccount);
        PVPCreatureAttributeComponent component =
            playerUnit.getComponentContainer().getComponent(UnitComponentType.ATTRIBUTE);
        long currentMp = playerUnit.getCurrentMp();
        long currentHp = playerUnit.getCurrentHp();

        double originHpRatio = currentHp / component.getAttributeValue(AttributeType.MAX_HP);
        double originMpRatio = currentMp / component.getAttributeValue(AttributeType.MAX_MP);

        component.setFinalAttributes(finalAttrs);
        component.setModelAttributeSet(modeAttributeSet);

        playerUnit.setCurrentHp((long)(component.getAttributeValue(AttributeType.MAX_HP) * originHpRatio));
        playerUnit.setCurrentMp((long)(component.getAttributeValue(AttributeType.MAX_MP) * originMpRatio));

    }
}
