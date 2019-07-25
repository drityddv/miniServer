package game.base.fight.model.skill.action.handler.impl;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.skill.model.BaseSkill;

/**
 * @author : ddv
 * @since : 2019/7/24 11:35 PM
 */

public class Avatar extends BaseActionHandler {

    @Override
    public void init(BaseCreatureUnit caster, List<BaseCreatureUnit> defenders, BaseCreatureUnit defender,
        BaseSkill baseSkill) {
        super.init(caster, defenders, defender, baseSkill);
        this.defender = caster;
        this.defenders = new ArrayList<>();
        this.defenders.add(defender);
    }
}
