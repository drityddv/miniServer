package game.world.fight.command.skill;

import java.util.List;

import game.base.skill.constant.SkillTypeEnum;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/16 12:30 PM
 */

public class UseGroupPointSkillCommand extends AbstractSkillCommand {

    public UseGroupPointSkillCommand(Player player, long skillId, List<Long> targetIds) {
        super(player, skillId, targetIds);
    }

    public static UseGroupPointSkillCommand valueOf(Player player, long skillId, List<Long> targetIds) {
        UseGroupPointSkillCommand command = new UseGroupPointSkillCommand(player, skillId, targetIds);
        return command;
    }

    @Override
    protected boolean isSkillLegality() {
        return battleParam.getBaseSkill().getSkillType() == SkillTypeEnum.Group_Point;
    }
}
