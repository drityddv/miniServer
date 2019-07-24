package game.world.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.base.fight.model.componet.IUnitComponent;
import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.gm.packet.SM_LogMessage;
import game.map.base.AbstractScene;
import game.map.model.Grid;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.PlayerVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;
import game.map.visible.impl.NpcVisibleObject;
import game.role.player.model.Player;
import game.role.skill.model.SkillEntry;
import game.role.skill.model.SkillList;
import net.utils.PacketUtil;
import utils.StringUtil;

/**
 * @author : ddv
 * @since : 2019/7/11 4:17 PM
 */

public class MapUtil {

    public static void log(Player player, AbstractScene scene, List<PlayerVisibleMapObject> visibleObjects,
        Collection<NpcVisibleObject> npcList, Collection<MonsterVisibleMapObject> monsters,
        Collection<BaseCreatureBuff> buffs) {
        StringBuilder sb = new StringBuilder();

        sb.append(StringUtil.wipePlaceholder("当前地图所处线程[{}]", Thread.currentThread().getName()));
        sb.append(StringUtil.wipePlaceholder("当前打印地图[{}]", scene.getMapId()));
        sb.append(StringUtil.wipePlaceholder("地图内玩家数量[{}]", visibleObjects.size()));

        visibleObjects.forEach(info -> {
            BaseCreatureUnit unit = info.getFighterAccount().getCreatureUnit();
            sb.append(StringUtil.wipePlaceholder("玩家[{}] 等级[{}] 生命值[{}] 法力值[{}] 坐标[{},{}] 上次移动时间戳[{}]",
                info.getAccountId(), unit.getLevel(), unit.getCurrentHp(), unit.getCurrentMp(),
                info.getCurrentGrid().getX(), info.getCurrentGrid().getY(), info.getLastMoveAt()));
            FighterAccount fighterAccount = info.getFighterAccount();
            Map<UnitComponentType, IUnitComponent> component =
                fighterAccount.getCreatureUnit().getComponentContainer().getTypeToComponent();
            component.forEach((type, iUnitComponent) -> {
                sb.append(StringUtil.wipePlaceholder("玩家战斗组件类型[{}]", type.name()));
                // if (iUnitComponent instanceof PVPCreatureAttributeComponent) {
                // PVPCreatureAttributeComponent attributeComponent = (PVPCreatureAttributeComponent)iUnitComponent;
                // AttributeUtils.logAttrs(attributeComponent, sb);
                // }

                // if (iUnitComponent instanceof PVPSkillComponent) {
                // PVPSkillComponent skillComponent = (PVPSkillComponent)iUnitComponent;
                // SkillSquare skillSquare = skillComponent.getSkillSquare();
                // sb.append(StringUtil.wipePlaceholder("技能栏下标[{}]", skillSquare.getSquareIndex()));
                // skillSquare.getSquareSkills().forEach((skillId, skillEntry) -> {
                // sb.append(StringUtil.wipePlaceholder("技能id[{}] 技能等级[{}]", skillId, skillEntry.getLevel()));
                // });
                // }
                // });
            });
        });

        sb.append(StringUtil.wipePlaceholder("地图内npc数量[{}]", npcList.size()));
        npcList.forEach(npcVisibleInfo -> {
            sb.append(StringUtil.wipePlaceholder("NPC id[{}],名称[{}] 坐标[{},{}]", npcVisibleInfo.getId(),
                npcVisibleInfo.getName(), npcVisibleInfo.getCurrentGrid().getX(),
                npcVisibleInfo.getCurrentGrid().getY()));
        });

        if (monsters != null) {
            sb.append(StringUtil.wipePlaceholder("地图内怪物数量[{}]", monsters.size()));
            for (MonsterVisibleMapObject monster : monsters) {
                BaseCreatureUnit monsterUnit = monster.getFighterAccount().getCreatureUnit();
                sb.append(StringUtil.wipePlaceholder("怪物id[{}]  名称[{}] 生命值[{}] 法力值[{}] 坐标[{},{}]",
                    monster.getFighterAccount().getCreatureUnit().getId(), monster.getMonsterName(),
                    monsterUnit.getCurrentHp(), monsterUnit.getCurrentMp(), monster.getCurrentGrid().getX(),
                    monster.getCurrentGrid().getY()));
            }
        }

        if (buffs != null) {
            sb.append(StringUtil.wipePlaceholder("地图内周期性buff注册数量 [{}]", buffs.size()));
//            for (BaseCreatureBuff buff : buffs) {
//                sb.append(StringUtil.wipePlaceholder("jobId[{}] 释放者[{}] ", buff.getJobId(), buff.getCaster().getId()));
//            }
        }

        String logFile = sb.toString();
        PacketUtil.send(player, SM_LogMessage.valueOf(logFile));

    }

    public static void logPlayerSkill(SkillList skillList, StringBuffer sb) {
        sb.append(StringUtil.wipePlaceholder("默认技能栏[{}]", skillList.getDefaultSquareIndex()));
        skillList.getSkills().forEach((skillId, skillEntry) -> {
            sb.append(StringUtil.wipePlaceholder("技能id[{}] 技能等级[{}] hashcode[{}]", skillId, skillEntry.getLevel(),
                skillEntry.hashCode()));
        });

        skillList.getSkillSquareMap().forEach((index, skillSquare) -> {
            sb.append(StringUtil.wipePlaceholder("技能栏[{}]", index));
            for (SkillEntry skillEntry : skillSquare.getSquareSkills().values()) {
                sb.append(StringUtil.wipePlaceholder("	技能id[{}] 技能等级[{}] hashcode[{}]", skillEntry.getSkillId(),
                    skillEntry.getLevel(), skillEntry.hashCode()));
            }
        });
    }

    public static boolean doMove(AbstractVisibleMapObject object, int[][] blockData) {
        int targetX = object.getTargetGrid().getX();
        int targetY = object.getTargetGrid().getY();

        try {
            if (object.getFighterAccount().getCreatureUnit().isCanMove()) {
                int blockPoint = blockData[targetX][targetY];
                if (blockPoint == 1) {
                    object.doMove();
                    return true;
                }
            }
        } catch (NullPointerException e) {
            RequestException.throwException(I18N.TARGET_POSITION_ERROR);
        }
        return false;
    }

    // 计算距离
    public static double calculateDistance(Grid grid, Grid targetGrid) {
        int x = grid.getX() - targetGrid.getX();
        int y = grid.getY() - targetGrid.getY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    // 格子是否合法
    public static boolean isGridLegal(Grid target, int[][] blockData) {
        int x = target.getX();
        int y = target.getY();
        try {
            int data = blockData[x][y];
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    // 坐标是否合法
    public static boolean isGridLegal(int x, int y, int[][] blockData) {
        try {
            int data = blockData[x][y];
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}