package game.world.fight.command.skill;

import game.base.skill.constant.SkillTypeEnum;
import game.map.area.CenterTypeEnum;
import game.map.model.Grid;
import game.role.player.model.Player;

/**
 * 使用aoe技能命令
 *
 * @author : ddv
 * @since : 2019/7/22 5:02 PM
 */

public class UseAoeSkillCommand extends AbstractSkillCommand {

    public UseAoeSkillCommand(Player player, long skillId) {
        super(player, skillId);
    }

    public static UseAoeSkillCommand valueOf(Player player, long skillId, Grid center, int centerType) {
        UseAoeSkillCommand command = new UseAoeSkillCommand(player, skillId);
        command.battleParam.setCenter(center);
        command.battleParam.setCenterTypeEnum(CenterTypeEnum.getById(centerType));
        return command;
    }

    @Override
    protected boolean isSkillLegality() {
        return battleParam.getBaseSkill().getSkillType() == SkillTypeEnum.Aoe;
    }

}
