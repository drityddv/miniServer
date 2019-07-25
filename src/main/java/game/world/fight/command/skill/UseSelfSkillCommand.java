package game.world.fight.command.skill;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.message.exception.RequestException;
import game.base.skill.constant.SkillTypeEnum;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;
import net.utils.PacketUtil;

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
    public void action() {
        try {
            AbstractMapHandler mapHandler = battleParam.getMapHandler();
            BaseActionHandler actionHandler = battleParam.getActionHandler();

            PlayerUnit caster = battleParam.getCaster();
            if (baseSkill.getSkillType() != SkillTypeEnum.Self) {
                return;
            }

            List<BaseCreatureUnit> defenders = new ArrayList<>();
            defenders.add(caster);
            actionHandler.init(caster, defenders, caster, baseSkill);
            actionHandler.action(caster, baseSkill);
            mapHandler.doLogMap(player, mapId);

        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
