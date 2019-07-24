package game.world.fight.command.skill;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.message.exception.RequestException;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.model.BaseSkill;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 12:30 PM
 */

public class UseSinglePointSkillCommand extends AbstractSceneCommand {
    private Player player;
    private long skillId;
    private long targetId;

    public UseSinglePointSkillCommand(int mapId) {
        super(mapId);
    }

    public static UseSinglePointSkillCommand valueOf(Player player, long skillId, long targetId) {
        UseSinglePointSkillCommand command = new UseSinglePointSkillCommand(player.getCurrentMapId());
        command.player = player;
        command.skillId = skillId;
        command.targetId = targetId;
        return command;
    }

    @Override
    public void action() {
        try {

            BattleParam battleParam = BattleUtil.initBattleParam(mapId, skillId, player.getPlayerId(), targetId, null);
            BaseActionHandler actionHandler = battleParam.getActionHandler();

            PlayerUnit caster = battleParam.getCaster();

            BaseSkill baseSkill = BattleUtil.getUnitSkill(caster, skillId);

            if (baseSkill.getSkillType() != SkillTypeEnum.Single_Point) {
                return;
            }

            BaseCreatureUnit defender = battleParam.getTargetUnit();
            if (defender == null) {
                return;
            }
            actionHandler.init(caster, null, defender, BattleUtil.getUnitSkill(caster, skillId));
            actionHandler.action(caster, defender, baseSkill);

        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
