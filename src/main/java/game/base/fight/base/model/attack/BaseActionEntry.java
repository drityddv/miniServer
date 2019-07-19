package game.base.fight.base.model.attack;

import java.util.ArrayList;
import java.util.List;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;

/**
 * 行为实体类
 *
 * @author : ddv
 * @since : 2019/7/16 3:42 PM
 */

public abstract class BaseActionEntry {
    protected ActionTypeEnum actionType;
    protected BaseSkill skill;
    protected long value;
    protected BaseCreatureUnit caster;
    protected List<BaseCreatureUnit> defenders = new ArrayList<>();

    public BaseActionEntry(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill skill, long value,
        ActionTypeEnum typeEnum) {
        this.value = value;
        this.actionType = typeEnum;
        this.skill = skill;
        this.caster = caster;
        this.defenders.add(defender);
    }

    public BaseActionEntry(BaseCreatureUnit caster, List<BaseCreatureUnit> defenders, BaseSkill skill, long value,
        ActionTypeEnum typeEnum) {
        this.skill = skill;
        this.actionType = typeEnum;
        this.caster = caster;
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
