package game.base.fight.base.model.attack;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;

/**
 * 攻击实体类
 *
 * @author : ddv
 * @since : 2019/7/16 3:42 PM
 */

public abstract class BaseActionEntry {
    protected BaseSkill skill;
    protected long value;
    protected BaseCreatureUnit attacker;
    protected List<BaseCreatureUnit> defenders = new ArrayList<>();

    public BaseActionEntry(BaseCreatureUnit attacker, BaseCreatureUnit defender, BaseSkill skill, long value) {
        this.value = value;
        this.skill = skill;
        this.attacker = attacker;
        this.defenders.add(defender);
    }

    public BaseActionEntry(BaseCreatureUnit attacker, List<BaseCreatureUnit> defenders, BaseSkill skill, long value) {
        this.skill = skill;
        this.attacker = attacker;
        this.defenders = defenders;
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    /**
     * 技能生效[伤害,效果...]
     */
    public abstract void doActive();
}
