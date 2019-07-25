package game.base.buff.model.impl;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.resource.BuffResource;
import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;

/**
 * @author : ddv
 * @since : 2019/7/24 5:35 PM
 */

public class PoisonCycleBuff extends BaseCycleBuff {
    /**
     * 层数
     */
    private int level;
    /**
     * 伤害
     */
    private long damage;

    @Override
    public void init(BuffResource buffResource, BuffContext context) {
        super.init(buffResource, context);
        level = context.getParam(BuffContextParamEnum.POISON_LEVEL);
        damage = context.getParam(BuffContextParamEnum.POISON_DAMAGE);
        modifyContext();
    }

    private void modifyContext() {
        this.context.addParam(BuffContextParamEnum.POISON_LEVEL, level);
        this.context.addParam(BuffContextParamEnum.POISON_DAMAGE, damage);
    }

    @Override
    public void merge(BaseCreatureBuff buff) {
        if (buff instanceof PoisonCycleBuff) {
            PoisonCycleBuff newBuff = (PoisonCycleBuff)buff;
            this.level += newBuff.level;
            this.damage += newBuff.damage;

            this.mergedCount++;
            this.remainCount += newBuff.remainCount;
            this.periodCount = remainCount;
            this.frequencyTime = Math.min(this.frequencyTime, newBuff.frequencyTime);
            modifyContext();
        }
		initBuffJob();

    }
}
