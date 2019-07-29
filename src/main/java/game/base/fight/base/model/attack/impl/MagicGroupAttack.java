package game.base.fight.base.model.attack.impl;

import game.base.fight.base.model.BaseActionEntry;
import game.base.fight.base.model.attack.ActionTypeEnum;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;
import game.world.fight.model.BattleParam;

/**
 * @author : ddv
 * @since : 2019/7/22 5:42 PM
 */

public class MagicGroupAttack extends BaseActionEntry {

    public MagicGroupAttack(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill skill, long value,
        BattleParam battleParam) {
        super(caster, defender, skill, value, ActionTypeEnum.Magic_Attack, battleParam);
    }

    public static MagicGroupAttack valueOf(BaseCreatureUnit caster, BaseCreatureUnit defender, BaseSkill baseSkill,
        long originValue, BattleParam battleParam) {
        MagicGroupAttack attack = new MagicGroupAttack(caster, defender, baseSkill, originValue, battleParam);
        return attack;
    }

    @Override
    public void doActive() {
        calculateAttack();
    }
}
