package game.world.fight.command.skill;

import java.util.List;

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

public class UseGroupPointSkillCommand extends AbstractSkillCommand {

    public UseGroupPointSkillCommand(Player player, long skillId, List<Long> targetIds) {
        super(player, skillId, targetIds);
    }

    public static UseGroupPointSkillCommand valueOf(Player player, long skillId, List<Long> targetIds) {
        UseGroupPointSkillCommand command = new UseGroupPointSkillCommand(player, skillId, targetIds);
        return command;
    }

    @Override
    public void action() {
        try {
            BaseActionHandler actionHandler = battleParam.getActionHandler();
            PlayerUnit caster = battleParam.getCaster();
            List<BaseCreatureUnit> targetUnits = battleParam.getTargetUnits();
            actionHandler.init(caster, targetUnits, null, baseSkill);
            actionHandler.action(caster, targetUnits, baseSkill);
            battleParam.getMapHandler().doLogMap(player, mapId);
        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isSkillLegality() {
        return baseSkill.getSkillType() == SkillTypeEnum.Group_Point;
    }
}
