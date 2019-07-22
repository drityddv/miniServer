package game.world.fight.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.pvpunit.PlayerUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;
import game.base.skill.constant.SkillTypeEnum;
import game.base.skill.model.BaseSkill;
import game.map.area.BaseAreaProcess;
import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.fight.model.BattleParam;

/**
 * 使用aoe技能命令
 *
 * @author : ddv
 * @since : 2019/7/22 5:02 PM
 */

public class UseAoeSkillCommand extends AbstractSceneCommand {
    private Player player;
    private long skillId;
    private Grid center;

    public UseAoeSkillCommand(int mapId) {
        super(mapId);
    }

    public static UseAoeSkillCommand valueOf(Player player, long skillId, Grid center) {
        UseAoeSkillCommand command = new UseAoeSkillCommand(player.getCurrentMapId());
        command.player = player;
        command.skillId = skillId;
        command.center = center;
        return command;
    }

    @Override
    public void action() {
        try {
            BattleParam battleParam = BattleUtil.initBattleParam(mapId, skillId, player.getPlayerId(), 0, null);
            AbstractMapHandler mapHandler = battleParam.getMapHandler();
            BaseActionHandler actionHandler = battleParam.getActionHandler();
            AbstractMovableScene mapScene = battleParam.getMapScene();

            PlayerUnit caster = battleParam.getCaster();
            BaseSkill baseSkill = BattleUtil.getUnitSkill(caster, skillId);

            if (baseSkill.getSkillType() != SkillTypeEnum.Aoe) {
                return;
            }

            BaseAreaProcess baseAreaProcess = baseSkill.getSkillLevelResource().getAreaTypeEnum().create();
            Map<String, Object> params = new HashMap<>();
            params.put("center", center);
            params.put("radius", Integer.parseInt(baseSkill.getSkillLevelResource().getAreaTypeParam().get("radius")));
            baseAreaProcess.init(params, mapScene);
            List<Grid> aoeGrids = baseAreaProcess.getResult();
            List<BaseCreatureUnit> targetUnits = mapScene.getCreatureUnits(aoeGrids);

            actionHandler.init(caster, targetUnits, null, baseSkill);
            actionHandler.action(caster, targetUnits, baseSkill);
            mapHandler.doLogMap(player, mapId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
