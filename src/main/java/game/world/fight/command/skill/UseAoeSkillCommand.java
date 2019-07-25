package game.world.fight.command.skill;

import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.message.exception.RequestException;
import game.base.skill.constant.SkillTypeEnum;
import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.map.model.Grid;
import game.role.player.model.Player;
import net.utils.PacketUtil;
import utils.CollectionUtil;

/**
 * 使用aoe技能命令
 *
 * @author : ddv
 * @since : 2019/7/22 5:02 PM
 */

public class UseAoeSkillCommand extends AbstractSkillCommand {
    private Grid center;

    public UseAoeSkillCommand(int mapId, Player player, long skillId) {
        super(mapId, player, skillId, CollectionUtil.emptyArrayList());
    }

    public static UseAoeSkillCommand valueOf(Player player, long skillId, Grid center) {
        UseAoeSkillCommand command = new UseAoeSkillCommand(player.getCurrentMapId(), player, skillId);
        command.center = center;
        return command;
    }

    @Override
    protected boolean isSkillLegality() {
        return baseSkill.getSkillType() == SkillTypeEnum.Aoe;
    }

    @Override
    public void action() {
        try {
            AbstractMapHandler mapHandler = battleParam.getMapHandler();
            BaseActionHandler actionHandler = battleParam.getActionHandler();
            AbstractMovableScene mapScene = battleParam.getMapScene();

            PlayerUnit caster = battleParam.getCaster();

            BaseAreaProcess baseAreaProcess = baseSkill.getSkillLevelResource().getAreaTypeEnum().create();

            AreaProcessParam param = AreaProcessParam.valueOf(center,
                Integer.parseInt(baseSkill.getSkillLevelResource().getAreaTypeParam().get("radius")));
            baseAreaProcess.init(param, mapScene);
            battleParam.setTargetUnits(baseAreaProcess.getResult());

            actionHandler.init(caster, battleParam.getTargetUnits(), null, baseSkill);
            actionHandler.action(caster, battleParam.getTargetUnits(), baseSkill);
            mapHandler.doLogMap(player, mapId);

        } catch (RequestException e) {
            PacketUtil.send(player, e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
