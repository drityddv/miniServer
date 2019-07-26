package game.world.fight.command.skill;

import game.base.skill.constant.SkillTypeEnum;
import game.role.player.model.Player;

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
        return command;
    }

    @Override
    protected boolean isSkillLegality() {
        return battleParam.getBaseSkill().getSkillType() == SkillTypeEnum.Single_Point;
    }
}
