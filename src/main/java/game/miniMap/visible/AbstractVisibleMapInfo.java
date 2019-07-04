package game.miniMap.visible;

import java.util.HashMap;
import java.util.Map;

import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/7/3 下午2:37
 */

public abstract class AbstractVisibleMapInfo {

    protected boolean move = true;
    protected int currentX = 0;
    protected int currentY = 0;
    protected int targetX = 0;
    protected int targetY = 0;
    // 上次移动时间 防外挂
    protected long lastMoveAt;
    protected boolean isInMap;
    // 模块可视信息
    private Map<Integer, Object> modelVisibleInfo = new HashMap<>();

    // clear 字段内网视野有个范围内可见玩家的list 防止后续要用到
    public void init(int currentX, int currentY, boolean clear) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = currentX;
        this.targetY = currentY;
        this.move = true;
        this.lastMoveAt = TimeUtil.now();
    }

    public boolean isExistInMap() {
        return isInMap;
    }

    /**
     * 这里先写死瞬移 后期再改
     */
    public void doMove() {
        this.currentX = targetX;
        this.currentY = targetY;
    }

    public abstract long getId();

    public abstract String getAccountId();

    // get and set

    public Map<Integer, Object> getModelVisibleInfo() {
        return modelVisibleInfo;
    }

    public void setModelVisibleInfo(Map<Integer, Object> modelVisibleInfo) {
        this.modelVisibleInfo = modelVisibleInfo;
    }

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

    public boolean isInMap() {
        return isInMap;
    }

    public void setInMap(boolean inMap) {
        isInMap = inMap;
    }
}
