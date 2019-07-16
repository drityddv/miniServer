package game.base.fight.model.skill.action.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.utils.BattleUtil;

/**
 * 普通造成伤害技能
 *
 * @author : ddv
 * @since : 2019/7/16 10:24 AM
 */

public class DefaultAttackHandler extends BaseActionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAttackHandler.class);

    @Override
    public void doAction(BaseCreatureUnit caster, BaseCreatureUnit defender, long skillId) {
        BattleUtil.doAttack(caster, defender, skillId);
    }
}
