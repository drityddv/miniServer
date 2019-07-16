package game.world.fight.command;

import java.util.Map;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.skill.model.BaseSkill;
import game.map.handler.AbstractMapHandler;
import game.map.visible.PlayerVisibleMapInfo;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/16 12:30 PM
 */

public class UseSinglePointSkillCommand extends AbstractSceneCommand {
    private Player player;
    private long skillId;
    private long targetId;

    public UseSinglePointSkillCommand(String accountId, int mapId) {
        super(accountId, mapId);
    }

    public static UseSinglePointSkillCommand valueOf(Player player, long skillId, long targetId) {
        UseSinglePointSkillCommand command =
            new UseSinglePointSkillCommand(player.getAccountId(), player.getCurrentMapId());
        command.player = player;
        command.skillId = skillId;
        command.targetId = targetId;
        return command;
    }

    @Override
    public void action() {
        try {
            AbstractMapHandler mapHandler = AbstractMapHandler.getAbstractMapHandler(mapId);
            BaseActionHandler actionHandler;

            Map<Long, PlayerVisibleMapInfo> playerObjects = mapHandler.getPlayerObjects(mapId);
            PlayerUnit caster =
                (PlayerUnit)playerObjects.get(player.getPlayerId()).getFighterAccount().getCreatureUnit();

            BaseSkill skill = BattleUtil.getUnitSkill(caster, skillId);
            actionHandler = AbstractMapHandler.getActionHandler(skill.getSkillLevelResource().getSkillType());
            BaseCreatureUnit defender = BattleUtil.findTargetUnit(mapHandler, targetId, mapId);
            if (defender == null) {
                return;
            }
            actionHandler.action(caster, defender, skillId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
