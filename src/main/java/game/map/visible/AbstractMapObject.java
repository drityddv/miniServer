package game.map.visible;

import game.base.fight.model.pvpunit.FighterAccount;
import game.map.model.Grid;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/3 下午2:37
 */

public abstract class AbstractMapObject {

    protected FighterAccount fighterAccount;

    protected Grid lastGrid;
    protected Grid currentGrid;
    protected Grid targetGrid;
    protected long lastMoveAt;

    public void init(int currentX, int currentY) {
        this.lastGrid = Grid.valueOf(currentX, currentY);
        this.currentGrid = Grid.valueOf(currentX, currentY);
        this.targetGrid = Grid.valueOf(currentX, currentY);
        this.lastMoveAt = TimeUtil.now();
    }

    /**
     * 这里先写死瞬移 后期再改
     */
    public void doMove() {
        lastGrid.setX(currentGrid.getX());
        lastGrid.setY(currentGrid.getY());

        currentGrid.setX(targetGrid.getX());
        currentGrid.setY(targetGrid.getY());

        this.lastMoveAt = TimeUtil.now();
    }

    /**
     * 单位id
     *
     * @return
     */
    public abstract long getId();

    /**
     * 单位名称
     *
     * @return
     */
    public abstract String getAccountId();

    // get and set

    public long getLastMoveAt() {
        return lastMoveAt;
    }

    public FighterAccount getFighterAccount() {
        return fighterAccount;
    }

    public void setFighterAccount(FighterAccount fighterAccount) {
        this.fighterAccount = fighterAccount;
    }

    public Grid getCurrentGrid() {
        return currentGrid;
    }

    public Grid getLastGrid() {
        return lastGrid;
    }

    public Grid getTargetGrid() {
        return targetGrid;
    }
}
