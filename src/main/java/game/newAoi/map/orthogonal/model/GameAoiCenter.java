package game.newAoi.map.orthogonal.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.newAoi.map.orthogonal.consant.OrthogonalNodeTypeEnum;
import game.newAoi.map.scene.Coordinate;
import game.newAoi.map.scene.MapScene;
import game.newAoi.map.scene.MapUnit;

/**
 * @author : ddv
 * @since : 2020/1/13 2:39 PM
 */

public class GameAoiCenter {

    private MapScene scene;

    /**
     * 动态维护 避免全图扫描 [当头指针]
     *
     * x轴维护 即同一个y维度的优先级后续所有x维度的点
     *
     * x轴维护的链表 y轴按照与x轴绝对距离 近的优先维护
     *
     * 0 0 1 0 0 , 0 1 0 0 0 , 0 1 1 0 1 , 0 0 1 0 1 ,
     *
     * node[1,1] : {1,1 -> 1,2 -> 2,0 -> 2,2 -> 2,3 -> 4,2 -> 4,3}
     */

    /**
     * x轴表头 [直接存在数组的[0,0]] 方便维护
     */
    private OrthogonalNode rowHead;
    /**
     * y轴表头 暂时用不到
     */
    private OrthogonalNode colHead;

    private Map<String, MapUnit> unitMap = new HashMap<>();

    public void init(MapScene scene) {
        Coordinate centerCoordinate = scene.getCenterCoordinate();
        rowHead = OrthogonalNode.valueOf(centerCoordinate.getX(), centerCoordinate.getY(),
            MapUnit.valueOf("x轴头节点", centerCoordinate.getX(), centerCoordinate.getY()), OrthogonalNodeTypeEnum.X_LIST);
        colHead = OrthogonalNode.valueOf(centerCoordinate.getX(), centerCoordinate.getY(),
            MapUnit.valueOf("y轴头节点", centerCoordinate.getX(), centerCoordinate.getY()), OrthogonalNodeTypeEnum.Y_LIST);
    }

    /**
     * 处理单位进入地图
     */
    public void handleJoinScene(MapUnit unit) {
        unitMap.put(unit.getUnitName(), unit);
        rowHead.insertRowNode(unit.getxNode());
    }

    /**
     * 查询所有节点
     */
    public List<Coordinate> getAllCoordinateList() {
        return rowHead.findAllCoordinateList();
    }

    public List<MapUnit> getVisibleList(MapUnit unit) {
        if (!unitMap.containsKey(unit.getUnitName())) {
            return Collections.emptyList();
        }
        return unit.getxNode().getVisibleList();
    }

    public OrthogonalNode getRowHead() {
        return rowHead;
    }

	public Map<String, MapUnit> getUnitMap() {
		return unitMap;
	}
}
