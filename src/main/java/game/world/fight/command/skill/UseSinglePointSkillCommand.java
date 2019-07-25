package game.world.fight.command.skill;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.message.exception.RequestException;
import game.base.skill.constant.SkillTypeEnum;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 12:30 PM
 */

public class UseSinglePointSkillCommand extends AbstractSkillCommand {

    public UseSinglePointSkillCommand(Player player, long skillId, long targetId) {
        super(player, skillId, targetId);
    }

    public static UseSinglePointSkillCommand valueOf(Player player, long skillId, long targetId) {
        UseSinglePointSkillCommand command = new UseSinglePointSkillCommand(player, skillId, targetId);
        command.player = player;
        command.skillId = skillId;
        return command;
    }

    @Override
    public void action() {
        try {
            BaseActionHandler actionHandler = battleParam.getActionHandler();
            PlayerUnit caster = battleParam.getCaster();

            if (baseSkill.getSkillType() != SkillTypeEnum.Single_Point) {
                return;
            }

            BaseCreatureUnit defender = battleParam.getTargetUnit();
            if (defender == null) {
                return;
            }
            actionHandler.init(battleParam);
            actionHandler.action(caster, defender, baseSkill);

        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
