package game.base.fight.model.skill.action.handler;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;
import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.area.CenterTypeEnum;
import game.map.model.Grid;
import game.world.fight.model.BattleParam;
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
        switch (battleParam.getSkillType()) {
            case Single_Point: {
                singlePoint(battleParam);
                break;
            }
            case Group_Point: {
                groupPoint(battleParam);
                break;
            }
            case Aoe: {
                aoeSkill(battleParam);
                break;
            }
            case Self: {
                selfSkill(battleParam);
                break;
            }
            default: {
                logger.warn("没有找到技能类型,忽略这次战斗");
            }
        }
    }

    private void aoeSkill(BattleParam battleParam) {
        actionPre(battleParam);
        Grid center;
        CenterTypeEnum centerTypeEnum = battleParam.getCenterTypeEnum();
        switch (centerTypeEnum) {
            case Self: {
                center = battleParam.getCaster().getMapObject().getCurrentGrid();
                break;
            }
            case Target_Grid: {
                center = battleParam.getCenter();
                break;
            }
            default: {
                return;
            }
        }
        BaseSkill baseSkill = battleParam.getBaseSkill();
        BaseAreaProcess baseAreaProcess = baseSkill.getSkillLevelResource().getAreaTypeEnum().getProcess();
        AreaProcessParam param = AreaProcessParam.valueOf(center,
            Integer.parseInt(baseSkill.getSkillLevelResource().getAreaTypeParam().get("radius")));
        battleParam.setTargetUnits(baseAreaProcess.calculate(param, battleParam.getMapScene()));
        doAction(battleParam.getCaster(), battleParam.getTargetUnits(), battleParam.getBaseSkill());
        actionAfter(battleParam);
    }

    private void singlePoint(BattleParam battleParam) {
        battleParam.loadSingleTarget();
        actionPre(battleParam);
        doAction(battleParam.getCaster(), Arrays.asList(battleParam.getTargetUnit()), battleParam.getBaseSkill());
        actionAfter(battleParam);
    }

    private void groupPoint(BattleParam battleParam) {
        battleParam.loadGroupTargets();
        actionPre(battleParam);
        doAction(battleParam.getCaster(), battleParam.getTargetUnits(), battleParam.getBaseSkill());
        actionAfter(battleParam);
    }

    private void selfSkill(BattleParam battleParam) {
        actionPre(battleParam);
        doAction(battleParam.getCaster(), Arrays.asList(battleParam.getCaster()), battleParam.getBaseSkill());
        actionAfter(battleParam);
    }

    private void actionAfter(BattleParam battleParam) {
        triggerAfterBuffs(battleParam);
    }

    private void actionPre(BattleParam battleParam) {
        skillConsume(battleParam.getCaster(), battleParam.getBaseSkill());
        triggerPreBuffs(battleParam);
    }

    private void triggerPreBuffs(BattleParam battleParam) {}

    private void triggerAfterBuffs(BattleParam battleParam) {}

    protected void doAction(BaseCreatureUnit caster, List<BaseCreatureUnit> targets, BaseSkill baseSkill) {
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
