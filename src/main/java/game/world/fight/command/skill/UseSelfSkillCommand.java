package game.world.fight.command.skill;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.message.exception.RequestException;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.model.BaseSkill;
import game.map.handler.AbstractMapHandler;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;
import net.utils.PacketUtil;

/**
 * 使用对自己释放的技能
 *
 * @author : ddv
 * @since : 2019/7/22 5:02 PM
 */

public class UseSelfSkillCommand extends AbstractSceneCommand {
    private Player player;
    private long skillId;

    public UseSelfSkillCommand(int mapId) {
        super(mapId);
    }

    public static UseSelfSkillCommand valueOf(Player player, long skillId) {
        UseSelfSkillCommand command = new UseSelfSkillCommand(player.getCurrentMapId());
        command.player = player;
        command.skillId = skillId;
        return command;
    }

    @Override
    public void action() {
        try {
            BattleParam battleParam = BattleUtil.initBattleParam(mapId, skillId, player.getPlayerId(), 0, null);
            AbstractMapHandler mapHandler = battleParam.getMapHandler();
            BaseActionHandler actionHandler = battleParam.getActionHandler();

            PlayerUnit caster = battleParam.getCaster();
            BaseSkill baseSkill = BattleUtil.getUnitSkill(caster, skillId);

            if (baseSkill.getSkillType() != SkillTypeEnum.Self) {
                return;
            }

            actionHandler.init(caster, null, null, baseSkill);
            actionHandler.action(caster, baseSkill);
            mapHandler.doLogMap(player, mapId);

        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
