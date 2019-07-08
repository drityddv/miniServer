package game.base.fight.model.pvpunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.game.attribute.util.AttributeUtils;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/5 下午4:57
 */

public class PlayerUnit extends BaseCreatureUnit {

    private static Logger logger = LoggerFactory.getLogger(PlayerUnit.class);

    static {
        Map<UnitComponentType, Class<? extends IUnitComponent>> map = new HashMap<>();
        map.put(UnitComponentType.ATTRIBUTE, PVPCreatureAttributeComponent.class);
        UnitComponentContainer.registerComponentClazz(PlayerUnit.class, map);
    }

    protected PlayerUnit() {
        super();
    }

    public PlayerUnit(long id) {
        super(id);
    }

    public static PlayerUnit valueOf(Player player, FighterAccount fighterAccount) {
        PlayerUnit playerUnit = new PlayerUnit();
        playerUnit.setId(player.getPlayerId());
        playerUnit.initComponent();
        playerUnit.fighterAccount = fighterAccount;
        playerUnit.setLevel(player.getLevel());
        playerUnit.setName(player.getAccountId());
        PVPCreatureAttributeComponent unitAttributeComponent = playerUnit.getAttributeComponent();
        PlayerAttributeContainer attributeContainer = player.getAttributeContainer();

        attributeContainer.getModelAttributeSet().forEach((attributeId, attributeSet) -> {
            List<Attribute> attributeList = new ArrayList<>();
            AttributeUtils.accumulateToMap(attributeSet, attributeList);
            unitAttributeComponent.putAttributes(attributeId, attributeList);
        });
        unitAttributeComponent.containerRecompute();
        return playerUnit;
    }

    public PVPCreatureAttributeComponent getAttributeComponent() {
        return componentContainer.getComponent(UnitComponentType.ATTRIBUTE);
    }

}
