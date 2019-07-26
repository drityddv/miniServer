package game.world.fight.command.skill;

import game.base.skill.constant.SkillTypeEnum;
import game.role.player.model.Player;

/**
 * 使用对自己释放的技能
 *
 * @author : ddv
 * @since : 2019/7/22 5:02 PM
 */

public class UseSelfSkillCommand extends AbstractSkillCommand {

    public UseSelfSkillCommand(Player player, long skillId) {
        super(player, skillId);
    }

    public static UseSelfSkillCommand valueOf(Player player, long skillId) {
        UseSelfSkillCommand command = new UseSelfSkillCommand(player, skillId);
        return command;
    }

    @Override
    protected boolean isSkillLegality() {
        return battleParam.getBaseSkill().getSkillType() == SkillTypeEnum.Self;
    }
}
