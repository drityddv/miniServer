package game.base.fight.trigger.result.impl;

/**
 * @author : ddv
 * @since : 2019/7/15 9:38 PM
 */

public class LifeChangeResult extends BaseExecuteResult<LifeChangeResult> {

    private long hp;
    private long mp;

    @Override
    protected void doMerge(LifeChangeResult result) {
        this.hp += result.hp;
        this.mp += result.mp;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public void alterHp(long hp) {
        this.hp += hp;
    }

    public void alterMp(long mp) {
        this.mp += mp;
    }
}
