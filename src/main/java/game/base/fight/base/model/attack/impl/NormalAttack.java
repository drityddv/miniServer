package game.base.fight.base.model.attack.impl;

import game.base.fight.base.model.attack.BaseAttackEntry;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 简单计算
 *
 * @author : ddv
 * @since : 2019/7/16 5:03 PM
 */

public class NormalAttack extends BaseAttackEntry {

    public NormalAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, long damage) {
        super(attacker, defender, damage);
    }

    public static NormalAttack valueOf(BaseCreatureUnit defender, long damage) {
        NormalAttack attack = new NormalAttack(null, defender, damage);
        return attack;
    }

    @Override
    public void doAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, long damage) {
        defender.defend(damage);
    }
}
