package game.newAoi.map.scene;

import java.util.ArrayList;
import java.util.List;

import game.newAoi.map.orthogonal.model.OrthogonalNode;

/**
 * @author : ddv
 * @since : 2020/1/13 2:42 PM
 */
public class MapUnit {
    private String unitName;
    /**
     * 坐标信息
     */
    private Coordinate coordinate;
    /**
     * x轴节点
     */
    private OrthogonalNode xNode;
    /**
     * y轴节点
     */
    private OrthogonalNode yNode;

    public static MapUnit valueOf(String name, int x, int y) {
        MapUnit unit = new MapUnit();
        unit.unitName = name;
        unit.coordinate = Coordinate.valueOf(x, y);
        unit.xNode = OrthogonalNode.valueOf(x, y, unit);
        return unit;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public OrthogonalNode getxNode() {
        return xNode;
    }

    public void setxNode(OrthogonalNode xNode) {
        this.xNode = xNode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public OrthogonalNode getyNode() {
        return yNode;
    }

    public void setyNode(OrthogonalNode yNode) {
        this.yNode = yNode;
    }

    public List<MapUnit> getVisibleList() {
        List<MapUnit> visibleList = new ArrayList<>();
        return visibleList;
    }
}
