package game.base.fight.model.pvpunit;

import game.base.fight.model.componet.UnitComponentContainer;

/**
 * pvp对象基础单元
 *
 * @author : ddv
 * @since : 2019/7/5 下午2:20
 */

public abstract class BaseUnit {
    protected UnitComponentContainer componentContainer = new UnitComponentContainer();
    protected long id;
    protected int level;
    protected boolean dead;
    protected boolean canMove;
    protected long currentHp;
    protected long currentMp;

    protected BaseUnit(long id) {
        this.id = id;
        this.dead = false;
        this.canMove = true;
    }

    public void initComponent() {
        componentContainer.initialize(this);
    }

    // 防御
    public void defend(long damage) {
        currentHp = currentHp > damage ? currentHp - damage : 0;
        if (currentHp == 0) {
            dead = true;
        }
    }

    // get and set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UnitComponentContainer getComponentContainer() {
        return componentContainer;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public long getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(long currentHp) {
        this.currentHp = currentHp;
    }

    public long getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(long currentMp) {
        this.currentMp = currentMp;
    }
}
