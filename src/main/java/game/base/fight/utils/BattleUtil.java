package game.base.fight.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import client.MessageEnum;
import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.BaseUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.model.skill.model.PVPSkillComponent;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.message.exception.RequestException;
import game.base.skill.model.BaseSkill;
import game.gm.packet.SM_LogMessage;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractMapObject;
import game.map.visible.PlayerMapObject;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;
import net.utils.PacketUtil;
import utils.StringUtil;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 3:24 PM
 */

public class BattleUtil {

    // 法力值是否足够
    public static boolean isEnoughMp(BaseUnit unit, long mpConsume) {
        return unit.getCurrentMp() >= mpConsume;
    }

    // 技能是否在cd
    public static boolean skillInCd(BaseSkill baseSkill) {
        return TimeUtil.now() < (baseSkill.getSkillCd() + baseSkill.getLastUsedAt());
    }

    // 获取战斗单元技能 可能为null
    public static BaseSkill getUnitSkill(BaseUnit unit, long skillId) {
        PVPSkillComponent component = unit.getComponentContainer().getComponent(UnitComponentType.SKILL);
        return component.getSkillMap().get(skillId);
    }

    public static PVPCreatureAttributeComponent getUnitAttrComponent(BaseUnit baseUnit) {
        return baseUnit.getComponentContainer().getComponent(UnitComponentType.ATTRIBUTE);
    }

    // 计算技能一级数值 [收到属性增益之后的数值]
    public static long calculateSkillValue1(long skillValue, List<AttributeType> attributeTypeList,
        Map<AttributeType, Attribute> attributes) {
        for (AttributeType attributeType : attributeTypeList) {
            Attribute attribute = attributes.get(attributeType);
            if (attribute != null) {
                skillValue += attribute.getValue();
            }
        }
        return skillValue;
    }

    // 找到怪物或者玩家
    public static BaseCreatureUnit findTargetUnit(AbstractMapHandler mapHandler, long targetId, int mapId) {
        BaseCreatureUnit unit = null;
        AbstractMapObject mapObject = mapHandler.getPlayerObjects(mapId).get(targetId);
        if (mapObject == null) {
            mapObject = mapHandler.getMonsterObjects(mapId).get(targetId);
        }
        if (mapObject == null) {
            return null;
        }
        unit = mapObject.getFighterAccount().getCreatureUnit();
        return unit;
    }

    // 可能返回空集合
    public static List<BaseCreatureUnit> findTargetUnits(AbstractMapHandler mapHandler, List<Long> targetIds,
        int mapId) {
        List<BaseCreatureUnit> units = new ArrayList<>();
        targetIds.forEach(targetId -> {
            BaseCreatureUnit unit = findTargetUnit(mapHandler, targetId, mapId);
            if (unit != null) {
                units.add(unit);
            }
        });
        return units;
    }

    public static void findTargetUnits(BattleParam battleParam) {

    }

    public static BattleParam loadParam(int mapId, long skillId, long playerId) {
        BattleParam battleParam = new BattleParam();

        AbstractMapHandler mapHandler = AbstractMapHandler.getAbstractMapHandler(mapId);
        BaseActionHandler actionHandler;

        Map<Long, PlayerMapObject> playerObjects = mapHandler.getPlayerObjects(mapId);
        PlayerMapObject playerMapObject = playerObjects.get(playerId);
        if (playerMapObject == null) {
            RequestException.throwException(MessageEnum.PLAYER_UNIT_NOT_EXIST);
        }

        PlayerUnit caster = (PlayerUnit)playerMapObject.getFighterAccount().getCreatureUnit();

        BaseSkill baseSkill = BattleUtil.getUnitSkill(caster, skillId);
        actionHandler = AbstractMapHandler.getActionHandler(baseSkill.getSkillLevelResource().getSkillEnum());

        battleParam.setBaseSkill(baseSkill);
        battleParam.setMapHandler(mapHandler);
        battleParam.setActionHandler(actionHandler);
        battleParam.setCaster(caster);
        battleParam.setMapScene(mapHandler.getMapScene(mapId));
        return battleParam;
    }

    public static BattleParam initTarget(int mapId, long skillId, long playerId, Long targetId) {
        BattleParam battleParam = loadParam(mapId, skillId, playerId);
        battleParam.setTargetUnit(BattleUtil.findTargetUnit(battleParam.getMapHandler(), targetId, mapId));
        return battleParam;
    }

    public static BattleParam initTargets(int mapId, long skillId, long playerId, List<Long> targetIds) {
        BattleParam battleParam = loadParam(mapId, skillId, playerId);

        if (targetIds != null) {
            List<BaseCreatureUnit> targetUnits =
                BattleUtil.findTargetUnits(battleParam.getMapHandler(), targetIds, mapId);
            battleParam.setTargetUnits(targetUnits);
        }

        return battleParam;
    }

    public static long getLongRandom(long left, long right) {
        return left + (((long)(new Random().nextDouble() * (right - left + 1))));
    }

    // 自行检查空指针
    public static long getUnitAttributeValue(BaseUnit unit, AttributeType type) {
        PVPCreatureAttributeComponent component =
            unit.getComponentContainer().getComponent(UnitComponentType.ATTRIBUTE);
        return component.getFinalAttributes().get(type).getValue();
    }

    public static PVPBuffComponent getUnitBuffComponent(BaseCreatureUnit unit) {
        PVPBuffComponent component = unit.getComponentContainer().getComponent(UnitComponentType.BUFF);
        return component;
    }

    /**
     * 打印战斗单元
     *
     * @param player
     * @param unit
     */
    public static void logUnit(Player player, BaseCreatureUnit unit) {
        StringBuilder sb = new StringBuilder();
        PVPBuffComponent buffComponent = unit.getComponentContainer().getComponent(UnitComponentType.BUFF);
        sb.append(StringUtil.wipePlaceholder("打印该单元释放的buff 长度[{}]", buffComponent.getCastBuffMap().size()));
        buffComponent.getCastBuffMap().forEach((buffId, buff) -> {
            sb.append(StringUtil.wipePlaceholder("buff id[{}] configId[{}]", buff.getBuffId(), buff.getConfigId()));
        });

        sb.append(StringUtil.wipePlaceholder("打印该单元拥有的buff 长度[{}]", buffComponent.getBuffMap().size()));
        buffComponent.getBuffMap().forEach((buffId, buff) -> {
            sb.append(StringUtil.wipePlaceholder("buff id[{}] configId[{}]", buff.getBuffId(), buff.getConfigId()));
        });

        sb.append(StringUtil.wipePlaceholder("打印该单元buff组 长度[{}]", buffComponent.getGroupBuffMap().size()));
        buffComponent.getBuffMap().forEach((buffId, buff) -> {
            sb.append(StringUtil.wipePlaceholder("buff id[{}] configId[{}] groupId[{}]", buff.getBuffId(),
                buff.getConfigId(), buff.getGroupId()));
        });
        PacketUtil.send(player, SM_LogMessage.valueOf(sb.toString()));
    }
}
