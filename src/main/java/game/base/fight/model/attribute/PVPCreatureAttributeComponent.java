package game.base.fight.model.attribute;

import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.game.attribute.AttributeContainer;
import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.util.AttributeUtils;

/**
 * @author : ddv
 * @since : 2019/7/5 下午4:10
 */

public class PVPCreatureAttributeComponent extends AttributeContainer<BaseCreatureUnit>
    implements IUnitComponent<BaseCreatureUnit> {

    public PVPCreatureAttributeComponent() {

    }

    public PVPCreatureAttributeComponent(BaseCreatureUnit creatureUnit) {
        super(creatureUnit);
    }

    public static PVPCreatureAttributeComponent valueOf(PlayerUnit playerUnit) {
        PVPCreatureAttributeComponent component = new PVPCreatureAttributeComponent(playerUnit);
        return component;
    }

    @Override
    public UnitComponentType getType() {
        return UnitComponentType.ATTRIBUTE;
    }

    @Override
    public void setOwner(BaseCreatureUnit owner) {
        this.owner = owner;
    }

    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        AttributeUtils.compute(modelAttributeSet, finalAttributes, records, accumulateAttributes, getOwner());
    }

}
