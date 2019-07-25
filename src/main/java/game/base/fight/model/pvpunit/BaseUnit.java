package game.base.fight.model.pvpunit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.base.model.attack.BaseActionEntry;
import game.base.fight.model.componet.UnitComponentContainer;

/**
 * pvp对象基础单元
 *
 * @author : ddv
 * @since : 2019/7/5 下午2:20
 */

public abstract class BaseUnit {

    private static final Logger logger = LoggerFactory.getLogger("战报");
    protected UnitComponentContainer componentContainer = new UnitComponentContainer();
    protected long id;
    protected int level;
    protected boolean dead;
    protected boolean canMove;
    protected long maxHp;
    protected long maxMp;
    protected long currentHp;
    protected long currentMp;
    protected int mapId;

    protected BaseUnit(long id) {
        this.id = id;
        this.dead = false;
        this.canMove = true;
    }

    public void initComponent() {
        componentContainer.initialize(this);
    }

    // 防御 自己扩展 可以走buff走生物自己的机制
    public void defend(BaseActionEntry attackEntry) {
        long damage = attackEntry.getValue();
        long realDamage = 0;
        realDamage = currentHp >= damage ? damage : currentHp;
        currentHp -= realDamage;
        if (currentHp == 0) {
            dead = true;
        }
        logger.info("id[{}] 受到伤害[{}] 剩余生命值[{}] 死亡状态[{}]", id, realDamage, currentHp, dead);
    }

    public void cureHp(long cureValue) {
        long realCure = 0;
        if (!isDead()) {
            if (!isHpFull()) {
                realCure = maxHp - currentHp >= cureValue ? cureValue : maxHp - currentHp;
                currentHp += realCure;
            }
        }
        logger.info("单位[{}] 死亡状态[{}] 治疗血量[{}] 实际治疗血量[{}]", id, isDead(), cureValue, realCure);
    }

    // 考虑溢出
    public boolean isHpFull() {
        return currentHp >= maxHp;
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

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public long getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(long currentMp) {
        this.currentMp = currentMp;
    }

    public long getMaxHp() {
        return maxHp;
    }

    public long getMaxMp() {
        return maxMp;
    }

	public void reviseStatus() {
	}
}
