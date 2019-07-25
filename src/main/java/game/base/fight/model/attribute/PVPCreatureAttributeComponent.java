package game.base.fight.model.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeContainer;
import game.base.game.attribute.AttributeSet;
import game.base.game.attribute.AttributeUpdateRecords;
import game.base.game.attribute.id.AttributeId;
import game.base.game.attribute.id.PvpBuffAttributeId;
import game.base.game.attribute.util.AttributeUtils;

/**
 * @author : ddv
 * @since : 2019/7/5 下午4:10
 */

public class PVPCreatureAttributeComponent extends AttributeContainer<BaseCreatureUnit>
    implements IUnitComponent<BaseCreatureUnit> {

    private Map<PvpBuffAttributeId, AttributeSet> buffAttributes = new HashMap<>();

    public PVPCreatureAttributeComponent() {}

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

    // pvp这边属性默认放大十倍 先屏蔽掉为了看数值变化
    @Override
    protected void recompute(AttributeUpdateRecords records, boolean needSyn) {
        AttributeUtils.compute(modelAttributeSet, finalAttributes, records, accumulateAttributes, getOwner());
        // finalAttributes.values().stream().filter(attribute -> attribute.getAttributeType() == AttributeType.MAX_MP
        // || attribute.getAttributeType() == AttributeType.MAX_HP).forEach(attribute -> {
        // attribute.alter(attribute.getValue() * 9);
        // });
    }

    // 同步需要手动从buff属性容器拉属性
    @Override
    public void putAttributesWithRecompute(AttributeId id, List<Attribute> attrs, boolean needSync) {
        if (id instanceof PvpBuffAttributeId) {
            AttributeSet attributeSet = AttributeSet.valueOf(attrs);
            buffAttributes.put((PvpBuffAttributeId)id, attributeSet);
        }
        super.putAttributesWithRecompute(id, attrs, needSync);
        owner.reviseStatus();
    }

    public void removeBuffAttribute(long buffId) {
        PvpBuffAttributeId attributeId = PvpBuffAttributeId.valueOf(buffId);
        buffAttributes.remove(attributeId);
        modelAttributeSet.remove(attributeId);
        containerRecompute();
		owner.reviseStatus();
    }
}
