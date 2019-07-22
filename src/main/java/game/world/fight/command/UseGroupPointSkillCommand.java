package game.world.fight.command;

import java.util.List;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.model.BaseSkill;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;

/**
 * @author : ddv
 * @since : 2019/7/16 12:30 PM
 */

public class UseGroupPointSkillCommand extends AbstractSceneCommand {
    private Player player;
    private long skillId;
    private List<Long> targetIds;

    public UseGroupPointSkillCommand(int mapId) {
        super(mapId);
    }

    public static UseGroupPointSkillCommand valueOf(Player player, long skillId, List<Long> targetIds) {
        UseGroupPointSkillCommand command = new UseGroupPointSkillCommand(player.getCurrentMapId());
        command.player = player;
        command.skillId = skillId;
        command.targetIds = targetIds;
        return command;
    }

    @Override
    public void action() {
        try {
            BattleParam battleParam = BattleUtil.initBattleParam(mapId, skillId, player.getPlayerId(), 0, targetIds);
            BaseActionHandler actionHandler = battleParam.getActionHandler();

            PlayerUnit caster = battleParam.getCaster();
            BaseSkill baseSkill = BattleUtil.getUnitSkill(caster, skillId);
            List<BaseCreatureUnit> targetUnits = battleParam.getTargetUnits();

            if (baseSkill.getSkillType() != SkillTypeEnum.Group_Point) {
                return;
            }
            actionHandler.init(caster, targetUnits, null, baseSkill);
            actionHandler.action(caster, targetUnits, baseSkill);
            battleParam.getMapHandler().doLogMap(player, mapId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
