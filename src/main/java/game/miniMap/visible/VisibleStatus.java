package game.miniMap.visible;

/**
 * @author : ddv
 * @since : 2019/7/3 下午2:59
 */

public enum VisibleStatus {
    /**
     * 正常状态
     */
    NORMAL(1, true),
    /**
     * 死亡
     */
    DEAD(2, false),;

    /**
     * id
     */
    private int id;
    /**
     * 是否可以移动
     */
    private boolean canMove;

    VisibleStatus(int id, boolean canMove) {
        this.id = id;
        this.canMove = canMove;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
}
