package game.base.fight.base.model.attack.impl;

import java.util.List;

import game.base.fight.base.model.attack.BaseActionEntry;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;

/**
 * 物理伤害型多体指向技能
 *
 * @author : ddv
 * @since : 2019/7/17 10:48 AM
 */

public class PhysicalGroupAttack extends BaseActionEntry {

    public PhysicalGroupAttack(BaseCreatureUnit attacker, List<BaseCreatureUnit> defenders, BaseSkill skill,
        long value) {
        super(attacker, defenders, skill, value);
    }

    public static PhysicalGroupAttack valueOf(BaseCreatureUnit attacker, List<BaseCreatureUnit> defenders,
        BaseSkill skill, long value) {
        PhysicalGroupAttack attack = new PhysicalGroupAttack(attacker, defenders, skill, value);
        return attack;
    }

    @Override
    public void doActive() {
        defenders.forEach(defender -> defender.defend(this));
    }
}
