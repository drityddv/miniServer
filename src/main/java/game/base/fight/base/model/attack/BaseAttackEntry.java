package game.base.fight.base.model.attack;

import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * 攻击实体类
 *
 * @author : ddv
 * @since : 2019/7/16 3:42 PM
 */

public abstract class BaseAttackEntry {
    protected long damage;
    protected BaseCreatureUnit attacker;
    protected BaseCreatureUnit defender;

    public BaseAttackEntry(BaseCreatureUnit attacker, BaseCreatureUnit defender, long damage) {

        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    /**
     * 攻击
     *
     * @param attacker
     * @param defender
     * @param damage
     */
    public abstract void doAttack(BaseCreatureUnit attacker, BaseCreatureUnit defender, long damage);
}
