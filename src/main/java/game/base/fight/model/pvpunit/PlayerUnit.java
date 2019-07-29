package game.base.fight.model.pvpunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.MessageEnum;
import game.base.fight.base.model.attack.BaseActionEntry;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.skill.model.PVPSkillComponent;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.game.attribute.util.AttributeUtils;
import game.map.visible.PlayerMapObject;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/7/5 下午4:57
 */

public class PlayerUnit extends BaseCreatureUnit {

    static {
        Map<UnitComponentType, Class<? extends IUnitComponent>> map = new HashMap<>();
        map.put(UnitComponentType.SKILL, PVPSkillComponent.class);
        UnitComponentContainer.registerComponentClazz(PlayerUnit.class, map);
    }

    private PlayerMapObject mapObject;

    public PlayerUnit(Player player, FighterAccount fighterAccount) {
        super(player.getPlayerId(), fighterAccount, player.getAccountId());
    }

    public static PlayerUnit valueOf(Player player, FighterAccount fighterAccount, int mapId) {
        PlayerUnit playerUnit = new PlayerUnit(player, fighterAccount);
        playerUnit.mapObject = (PlayerMapObject)fighterAccount.getMapObject();
        playerUnit.level = player.getLevel();
        playerUnit.initComponent();

        // 初始化属性组件 FIXME 复制一份
        PVPCreatureAttributeComponent unitAttributeComponent = playerUnit.getAttributeComponent();
        PlayerAttributeContainer attributeContainer = player.getAttributeContainer();
        attributeContainer.getModelAttributeSet().forEach((attributeId, attributeSet) -> {
            List<Attribute> attributeList = new ArrayList<>();
            AttributeUtils.accumulateToMap(attributeSet, attributeList);
            unitAttributeComponent.putAttributes(attributeId, attributeList);
        });

        playerUnit.initBaseAttribute();

        playerUnit.mapId = mapId;

        // 初始化技能组件
        PVPSkillComponent skillComponent = playerUnit.getSkillComponent();
        skillComponent.init(player, playerUnit);

        return playerUnit;
    }

    @Override
    protected void handlerDead(BaseActionEntry attackEntry) {
        if (!handleDead) {
            super.handlerDead(attackEntry);
            PacketUtil.send(mapObject.getPlayer(), MessageEnum.DEAD);
        }
    }

    @Override
    public void relive() {
        super.relive();
        PacketUtil.send(mapObject.getPlayer(), MessageEnum.RE_LIVE);
    }

    @Override
    public void reviseStatus() {
        double hpRadio = (double)currentHp / (double)maxHp;
        double mpRadio = (double)currentMp / (double)maxMp;
        PVPCreatureAttributeComponent unitAttrComponent = getAttributeComponent();
        maxHp = unitAttrComponent.getFinalAttributes().get(AttributeType.MAX_HP).getValue();
        maxMp = unitAttrComponent.getFinalAttributes().get(AttributeType.MAX_MP).getValue();
        currentHp = (long)(hpRadio * maxHp);
        currentMp = (long)(mpRadio * maxMp);
    }

    public PlayerMapObject getMapObject() {
        return mapObject;
    }
}
