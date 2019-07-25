package game.base.fight.model.skill.action.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.utils.BattleUtil;
import game.base.skill.model.BaseSkill;
import game.map.model.Grid;
import spring.SpringContext;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/16 10:18 AM
 */

public abstract class BaseActionHandler implements IActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BaseActionHandler.class);
    protected BaseCreatureUnit caster;
    protected BaseSkill baseSkill;
    protected BaseCreatureUnit defender;
    protected List<BaseCreatureUnit> defenders = new ArrayList<>();

    @Override
    public void action(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill skill) {
        run();
    }

    @Override
    public void action(BaseCreatureUnit caster, List<BaseCreatureUnit> target, BaseSkill skill) {
        run();
    }

    @Override
    public void action(BaseCreatureUnit caster, Grid center, BaseSkill baseSkill) {
        run();
    }

    @Override
    public void action(BaseCreatureUnit caster, BaseSkill baseSkill) {
        run();
    }



    private void run() {
        if (!actionPre()) {
            return;
        }
        doAction();
        actionAfter();
    }

    // 使用技能前的通用逻辑[检查技能合法性,记录cd等...] 子类按需重写
    protected boolean canUseSkill(BaseCreatureUnit caster, BaseSkill baseSkill) {

        if (baseSkill == null) {
            logger.warn("玩家[{}] 使用技能失败,技能为空", caster.getFighterAccount().getAccountId());
            return false;
        }

        if (!BattleUtil.isEnoughMp(caster, baseSkill.getSkillMpConsume())) {
            logger.warn("玩家[{}] 使用技能失败,魔法值不足 需要[{}] 实际拥有[{}]", caster.getFighterAccount().getAccountId(),
                baseSkill.getSkillMpConsume(), caster.getCurrentMp());
            return false;
        }

        if (BattleUtil.skillInCd(baseSkill)) {
            logger.warn("玩家[{}] 使用技能失败,技能cd中", caster.getFighterAccount().getAccountId());
            return false;
        }

        // 记录cd
        baseSkill.setLastUsedAt(TimeUtil.now());
        return true;
    }

    public void init(BaseCreatureUnit caster, List<BaseCreatureUnit> defenders, BaseCreatureUnit defender,
        BaseSkill baseSkill) {
        this.caster = caster;
        this.defender = defender;
        this.defenders = defenders;
        this.baseSkill = baseSkill;
    }

    protected void doAction() {
        triggerBuffs();
    }

    // 使用前
    protected boolean actionPre() {
        return canUseSkill(caster, baseSkill);
    }

    // 使用后
    protected void actionAfter() {

    }

    // 技能效果值
    public long getSkillValue() {
        return baseSkill.getSkillLevelResource().getValue();
    }

    private void triggerBuffs() {
        List<Long> buffList = baseSkill.getSkillLevelResource().getBuffList();
        SpringContext.getBuffService().addBuffInScene(buffList, caster, defenders);
    }
}
