package game.map.visible;

import game.base.fight.model.pvpunit.FighterAccount;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/3 下午2:37
 */

public abstract class AbstractVisibleMapInfo {

    protected FighterAccount fighterAccount;

    protected boolean move = true;
    protected int currentX = 0;
    protected int currentY = 0;
    protected int targetX = 0;
    protected int targetY = 0;
    protected long lastMoveAt;

    public void init(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = currentX;
        this.targetY = currentY;
        this.move = false;
        this.lastMoveAt = TimeUtil.now();
    }

    /**
     * 这里先写死瞬移 后期再改
     */
    public void doMove() {
        this.currentX = targetX;
        this.currentY = targetY;
        this.move = false;
        this.lastMoveAt = TimeUtil.now();
    }

    public abstract long getId();

    public abstract String getAccountId();

    // get and set

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public long getLastMoveAt() {
        return lastMoveAt;
    }

    public void setLastMoveAt(long lastMoveAt) {
        this.lastMoveAt = lastMoveAt;
    }

    public FighterAccount getFighterAccount() {
        return fighterAccount;
    }

    public void setFighterAccount(FighterAccount fighterAccount) {
        this.fighterAccount = fighterAccount;
    }
}
