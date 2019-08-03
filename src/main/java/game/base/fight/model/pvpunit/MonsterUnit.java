package game.base.fight.model.pvpunit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.effect.model.constant.RestrictStatusEnum;
import game.base.executor.util.ExecutorUtils;
import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentContainer;
import game.base.fight.model.componet.UnitComponentType;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.id.AttributeIdEnum;
import game.base.item.base.model.AbstractItem;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;
import game.world.base.command.player.AccountEbusPushCommand;
import game.world.base.command.player.AddItemToPackCommand;
import game.world.base.resource.CreatureResource;
import game.world.fight.event.KillMonsterEvent;
import game.world.instance.singleIntance.handler.SingleInstanceMapHandler;
import spring.SpringContext;

/**
 * 怪物单元
 *
 * @author : ddv
 * @since : 2019/7/8 上午9:23
 */

public class MonsterUnit extends BaseCreatureUnit {

    static {
        Map<UnitComponentType, Class<? extends IUnitComponent>> map = new HashMap<>();
        UnitComponentContainer.registerComponentClazz(MonsterUnit.class, map);
    }

    // 怪物配置表
    private CreatureResource creatureResource;

    public MonsterUnit(CreatureResource creatureResource, FighterAccount fighterAccount, long id) {
        super(id, fighterAccount, creatureResource.getObjectName());
    }

    public static MonsterUnit valueOf(CreatureResource creatureResource, FighterAccount fighterAccount, long id,
        int mapId, long sceneId) {
        MonsterUnit monsterUnit = new MonsterUnit(creatureResource, fighterAccount, id);
        monsterUnit.creatureResource = creatureResource;
        monsterUnit.fighterAccount = fighterAccount;
        monsterUnit.level = creatureResource.getLevel();
        monsterUnit.mapId = mapId;
        monsterUnit.sceneId = sceneId;

        monsterUnit.initComponent();
        PVPCreatureAttributeComponent unitAttributeComponent = monsterUnit.getAttributeComponent();
        unitAttributeComponent.putAttributes(AttributeIdEnum.BASE, creatureResource.getAttributeList());
        unitAttributeComponent.setOwner(monsterUnit);
        unitAttributeComponent.containerRecompute();

        monsterUnit.maxHp =
            monsterUnit.currentHp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_HP).getValue();
        monsterUnit.maxMp =
            monsterUnit.currentMp = unitAttributeComponent.getFinalAttributes().get(AttributeType.MAX_MP).getValue();
        monsterUnit.mapId = creatureResource.getMapId();
        return monsterUnit;
    }

    public CreatureResource getCreatureResource() {
        return creatureResource;
    }

    @Override
    public BaseCreatureUnit hatch(CreatureResource creatureResource, FighterAccount fighterAccount, long id, int mapId,
        long sceneId) {
        return MonsterUnit.valueOf(creatureResource, fighterAccount, id, mapId, sceneId);
    }

    @Override
    public void handlerDead(BaseActionEntry attackEntry) {
        BaseCreatureUnit caster = attackEntry.getCaster();
        if (handleDead) {
            return;
        }
        statusEnum = RestrictStatusEnum.DEAD;

        AbstractMapHandler mapHandler = attackEntry.getBattleParam().getMapHandler();
        if (mapHandler instanceof SingleInstanceMapHandler) {
			handleDead = true;
            mapHandler.handlerUnitDead(this);
            return;
        }
        super.handlerDead(attackEntry);
        // 触发发奖
        if (caster instanceof PlayerUnit) {
            PlayerUnit playerUnit = (PlayerUnit)caster;
            Player player = playerUnit.getMapObject().getPlayer();
            long dropConfigId = creatureResource.getDropConfigId();
            List<AbstractItem> rewardItems = SpringContext.getItemService().createItemsByDropConfig(dropConfigId);
            ExecutorUtils.submit(AddItemToPackCommand.valueOf(player, rewardItems));

            ExecutorUtils.submit(AccountEbusPushCommand.valueOf(player,
                KillMonsterEvent.valueOf(player, caster.getMapId(), creatureResource)));

        }

    }

}
