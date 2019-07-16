package game.base.fight.model.skill.action.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.componet.UnitComponentType;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.model.PVPSkillComponent;
import game.base.fight.utils.BattleUtil;
import game.base.skill.model.BaseSkill;

/**
 * @author : ddv
 * @since : 2019/7/16 10:18 AM
 */

public abstract class BaseActionHandler implements IActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BaseActionHandler.class);

    @Override
    public void action(BaseCreatureUnit caster, BaseCreatureUnit defender, long skillId) {
        if (!useSkillPre(caster, skillId)) {
            return;
        }
        doAction(caster, defender, skillId);
    }

    // 使用技能前的通用检查 子类按需重写
    protected boolean useSkillPre(BaseCreatureUnit caster, long skillId) {
        PVPSkillComponent skillComponent = caster.getComponentContainer().getComponent(UnitComponentType.SKILL);
        BaseSkill baseSkill = skillComponent.getSkillMap().get(skillId);

        if (baseSkill == null) {
            logger.warn("玩家[{}] 使用技能失败,玩家未学习该技能[{}]", caster.getFighterAccount().getAccountId(), skillId);
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
        return true;
    }

    /**
     * 使用
     *
     * @param caster
     * @param defender
     * @param skillId
     */
    public abstract void doAction(BaseCreatureUnit caster, BaseCreatureUnit defender, long skillId);
}
