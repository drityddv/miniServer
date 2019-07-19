package game.map.model;

import java.util.List;

import game.map.base.BroadcastCenter;

/**
 * @author : ddv
 * @since : 2019/7/3 下午5:00
 */

public class Grid {
    // FIXME 加入阻挡点
    private int x;
    private int y;
    private List<BroadcastCenter> currentCenter;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Grid grid = (Grid)o;

        if (x != grid.x) {
            return false;
        }
        return y == grid.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
