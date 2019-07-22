package game.base.fight.base.model.attack.impl;

import java.util.List;

import game.base.fight.base.model.attack.ActionTypeEnum;
import game.base.fight.base.model.attack.BaseActionEntry;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.base.skill.model.BaseSkill;

/**
 * @author : ddv
 * @since : 2019/7/22 5:42 PM
 */

public class MagicGroupAttack extends BaseActionEntry {

    public MagicGroupAttack(BaseCreatureUnit caster, List<BaseCreatureUnit> defenders, BaseSkill skill, long value) {
        super(caster, defenders, skill, value, ActionTypeEnum.Magic_Attack);
    }

    public static MagicGroupAttack valueOf(BaseCreatureUnit caster, List<BaseCreatureUnit> defenders,
        BaseSkill baseSkill, long originValue) {
        MagicGroupAttack attack = new MagicGroupAttack(caster, defenders, baseSkill, originValue);
        return attack;
    }

    @Override
    public void doActive() {

    }
}
