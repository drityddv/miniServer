package game.base.fight.model.skill.action.handler;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.constant.SkillParamConstant;
import game.base.skill.model.BaseSkill;
import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.area.CenterTypeEnum;
import game.map.model.Grid;
import game.world.fight.model.BattleParam;
import game.world.fight.packet.SM_BattleReport;
import net.utils.PacketUtil;
import spring.SpringContext;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 10:18 AM
 */

public abstract class BaseActionHandler implements IActionHandler {

    protected static final Logger logger = LoggerFactory.getLogger(BaseActionHandler.class);

    @Override
    public void action(BattleParam battleParam) {
        targetAction(battleParam);
    }

    private void targetAction(BattleParam battleParam) {
        actionPre(battleParam);
        Grid center;
        CenterTypeEnum centerTypeEnum = battleParam.getCenterTypeEnum();
        switch (centerTypeEnum) {
            case Self: {
                center = battleParam.getCaster().getMapObject().getCurrentGrid();
                loadWithAoe(battleParam, center);
                break;
            }
            case Target_Grid: {
                center = battleParam.getCenter();
                loadWithAoe(battleParam, center);
                break;
            }
            case Default: {
                battleParam.loadTargets();
                break;
            }
            default: {
                return;
            }
        }

        doAction(battleParam.getCaster(), battleParam.getTargetUnits(), battleParam.getBaseSkill(), battleParam);
        actionAfter(battleParam);
    }

    private void loadWithAoe(BattleParam battleParam, Grid center) {
        BaseSkill baseSkill = battleParam.getBaseSkill();
        BaseAreaProcess baseAreaProcess = baseSkill.getSkillLevelResource().getAreaTypeEnum().getProcess();
        AreaProcessParam param = AreaProcessParam.valueOf(center,
            Integer.parseInt(baseSkill.getSkillLevelResource().getAreaTypeParam().get(SkillParamConstant.RADIUS)));
        battleParam.setTargetUnits(baseAreaProcess.calculate(param, battleParam.getMapScene()));
    }

    private void actionAfter(BattleParam battleParam) {
        triggerAfterBuffs(battleParam);
        sendBattleResult(battleParam);
    }

    private void actionPre(BattleParam battleParam) {
        skillConsume(battleParam.getCaster(), battleParam.getBaseSkill());
        triggerPreBuffs(battleParam);
    }

    private void triggerPreBuffs(BattleParam battleParam) {}

    private void triggerAfterBuffs(BattleParam battleParam) {}

    private void sendBattleResult(BattleParam battleParam) {
        PacketUtil.send(battleParam.getPlayer(), SM_BattleReport.valueOf(battleParam));
    }

    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill,
        BattleParam battleParam) {
        triggerBuffs(caster, targets, baseSkill);
    }

    private void triggerBuffs(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill) {
        List<Long> buffList = baseSkill.getSkillLevelResource().getBuffList();
        SpringContext.getBuffService().addBuffInScene(buffList, caster, targets);
    }

    // 技能消耗逻辑
    private void skillConsume(BaseCreatureUnit caster, BaseSkill baseSkill) {
        caster.consumeMp(baseSkill.getSkillMpConsume());
        baseSkill.setLastUsedAt(TimeUtil.now());
    }

}
