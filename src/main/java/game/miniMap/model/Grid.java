package game.miniMap.model;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:00
 */

public class Grid {
    // FIXME 加入阻挡点
    private int x;
    private int y;

    public static Grid valueOf(int x, int y) {
        Grid grid = new Grid();
        grid.x = x;
        grid.y = y;
        return grid;
    }

    public boolean canWalk() {
        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
