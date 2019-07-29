package game.map.area;

import java.util.List;

import game.map.model.Grid;

/**
 * 子类各取所需
 *
 * @author : ddv
 * @since : 2019/7/23 4:31 PM
 */

public class AreaProcessParam {
    private Grid center;
    private int radius;
    private List<Long> targetIdList;

    public AreaProcessParam(Grid center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public static AreaProcessParam valueOf(Grid center, int radius) {
        return new AreaProcessParam(center, radius);
    }

    public Grid getCenter() {
        return center;
    }

    public List<Long> getTargetIdList() {
        return targetIdList;
    }

    public int getRadius() {
        return radius;
    }
}
