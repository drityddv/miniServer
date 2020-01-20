package game.newAoi.map.scene;

import java.util.Objects;

/**
 * 地图单位坐标信息封装
 *
 * @author : ddv
 * @since : 2020/1/13 2:47 PM
 */

public class Coordinate {

    private int x;
    private int y;

    public static Coordinate valueOf(int x, int y) {
        Coordinate coordinate = new Coordinate();
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
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
        Coordinate that = (Coordinate)o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
