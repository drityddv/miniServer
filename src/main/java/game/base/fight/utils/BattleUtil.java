package game.base.fight.utils;

import java.util.List;
import java.util.Map;

import client.MessageEnum;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.message.exception.RequestException;
import game.base.skill.model.BaseSkill;
import game.gm.packet.SM_LogMessage;
import game.map.area.CenterTypeEnum;
import game.map.handler.AbstractMapHandler;
import game.map.visible.PlayerMapObject;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;
import net.utils.PacketUtil;
import utils.StringUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 3:24 PM
 */

public class BattleUtil {

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

    public static BattleParam loadParam(int mapId, long sceneId, long skillId, long playerId) {
        BattleParam battleParam = new BattleParam();

        AbstractMapHandler mapHandler = AbstractMapHandler.getAbstractMapHandler(mapId);
        BaseActionHandler actionHandler;

        Map<Long, PlayerMapObject> playerObjects = mapHandler.getPlayerObjects(mapId, sceneId);
        PlayerMapObject playerMapObject = playerObjects.get(playerId);
        if (playerMapObject == null) {
            RequestException.throwException(MessageEnum.PLAYER_UNIT_NOT_EXIST);
        }

        PlayerUnit caster = (PlayerUnit)playerMapObject.getFighterAccount().getCreatureUnit();

        BaseSkill baseSkill = caster.getSkillComponent().getSKill(skillId);
        actionHandler = AbstractMapHandler.getActionHandler(baseSkill.getSkillLevelResource().getSkillEnum());

        battleParam.setCenterTypeEnum(CenterTypeEnum.Default);
        battleParam.setPlayer(caster.getMapObject().getPlayer());
        battleParam.setBaseSkill(baseSkill);
        battleParam.setMapHandler(mapHandler);
        battleParam.setActionHandler(actionHandler);
        battleParam.setCaster(caster);
        battleParam.setMapScene(mapHandler.getMapScene(mapId, sceneId));
        return battleParam;
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
