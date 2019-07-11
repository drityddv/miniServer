package game.world.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import game.base.fight.model.attribute.PVPCreatureAttributeComponent;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.game.attribute.util.AttributeUtils;
import game.gm.packet.SM_LogMessage;
import game.map.base.AbstractScene;
import game.map.visible.PlayerVisibleMapInfo;
import game.map.visible.impl.MonsterVisibleMapInfo;
import game.map.visible.impl.NpcVisibleInfo;
import game.role.player.model.Player;
import net.utils.PacketUtil;
import utils.StringUtil;

/**
 * @author : ddv
 * @since : 2019/7/11 4:17 PM
 */

public class MapUtil {

    public static void log(Player player, AbstractScene scene, List<PlayerVisibleMapInfo> visibleObjects,
        Collection<NpcVisibleInfo> npcList, Collection<MonsterVisibleMapInfo> monsters) {
        StringBuilder sb = new StringBuilder();

        sb.append(StringUtil.wipePlaceholder("当前地图所处线程[{}]", Thread.currentThread().getName()));
        sb.append(StringUtil.wipePlaceholder("当前打印地图[{}]", scene.getMapId()));
        sb.append(StringUtil.wipePlaceholder("地图内玩家数量[{}]", visibleObjects.size()));

        visibleObjects.forEach(info -> {
            sb.append(StringUtil.wipePlaceholder("玩家[{}],坐标[{},{}] 上次移动时间戳[{}]", info.getAccountId(),
                info.getCurrentX(), info.getCurrentY(), info.getLastMoveAt()));
            FighterAccount fighterAccount = info.getFighterAccount();
            Map<UnitComponentType, IUnitComponent> component =
                fighterAccount.getCreatureUnit().getComponentContainer().getTypeToComponent();
            component.forEach((type, iUnitComponent) -> {
                sb.append(StringUtil.wipePlaceholder("玩家战斗组件类型[{}]", type.name()));
                PVPCreatureAttributeComponent attributeComponent = (PVPCreatureAttributeComponent)iUnitComponent;
                AttributeUtils.logAttrs(attributeComponent, sb);
            });
        });

        sb.append(StringUtil.wipePlaceholder("地图内npc数量[{}]", npcList.size()));
        npcList.forEach(npcVisibleInfo -> {
            sb.append(StringUtil.wipePlaceholder("NPC id[{}],名称[{}] 坐标[{},{}]", npcVisibleInfo.getId(),
                npcVisibleInfo.getName(), npcVisibleInfo.getCurrentX(), npcVisibleInfo.getCurrentY()));
        });

        if (monsters != null) {
            sb.append(StringUtil.wipePlaceholder("地图内怪物数量[{}]", monsters.size()));
            for (MonsterVisibleMapInfo monster : monsters) {
                sb.append(StringUtil.wipePlaceholder("怪物id[{}] 名称[{}] ", monsters.size(), monster.getMonsterName()));
            }
        }

        String logFile = sb.toString();
        PacketUtil.send(player, SM_LogMessage.valueOf(logFile));
    }
}
