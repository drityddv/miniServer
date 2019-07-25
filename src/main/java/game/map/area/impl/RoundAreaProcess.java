package game.map.area.impl;

import java.util.List;
import java.util.stream.Collectors;

import game.map.area.AreaProcessParam;
import game.map.area.BaseAreaProcess;
import game.map.base.AbstractMovableScene;
import game.map.model.Grid;
import game.map.visible.AbstractMapObject;
import game.world.base.resource.MapBlockResource;
import game.world.utils.MapUtil;

/**
 * 计算逻辑:算出左上和右下 然后锁定矩形区域 再计算内部坐标点的距离
 *
 * @author : ddv
 * @since : 2019/7/22 2:11 PM
 */

public class RoundAreaProcess extends BaseAreaProcess {
    protected int radius;
    protected Grid center;
    protected Grid leftTop;
    protected Grid rightBottom;

    @Override
    public void init(AreaProcessParam param, AbstractMovableScene mapScene) {
        this.mapScene = mapScene;
        this.blockData = mapScene.getBaseMapInfo().getBlockResource().getBlockData();
        this.radius = param.getRadius();
        this.center = param.getCenter();
        this.leftTop = Grid.valueOf(center.getX(), center.getY());
        this.rightBottom = Grid.valueOf(center.getX(), center.getY());
        calculateCorner(mapScene.getBaseMapInfo().getBlockResource());
    }

    // FIXME 目前不支持奇形怪状的地图
    @Override
    public void calculate() {
        calculateByAoi();
    }

    /**
     * 通过aoi获取单位
     */
    private void calculateByAoi() {
        List<AbstractMapObject> areaObjects = mapScene.getAreaObjects(center);
        areaObjects = areaObjects.stream()
            .filter(mapObject -> MapUtil.calculateDistance(center, mapObject.getCurrentGrid()) <= radius)
            .collect(Collectors.toList());
        areaObjects.forEach(mapObject -> {
            targetUnits.add(mapObject.getFighterAccount().getCreatureUnit());
        });
    }

    /**
     * 自己计算影响坐标 再给出坐标向aoi获取单位
     */
    private void defaultCalculate() {
        int yDistance = rightBottom.getY() - leftTop.getY();
        int yPlus = 0;
        while (yDistance >= 0) {
            Grid grid = Grid.valueOf(leftTop);
            grid.setY(grid.getY() + yPlus);
            while (grid.getX() <= rightBottom.getX()) {
                checkGrid(grid);
                grid.plusX();
            }
            yDistance--;
            yPlus++;
        }
    }

    // 检验距离 加入结果集
    private void checkGrid(Grid grid) {
        double distance = MapUtil.calculateDistance(grid, center);
        if (distance <= radius) {
            targetGrids.add(Grid.valueOf(grid));
        }
    }

    private void calculateCorner(MapBlockResource blockResource) {
        while (center.getX() - leftTop.getX() < radius) {
            if (!blockResource.isGridLegal(leftTop.getX() - 1, leftTop.getY())) {
                break;
            }
            leftTop.decreaseX();
        }
        while (center.getY() - leftTop.getY() < radius) {
            if (!blockResource.isGridLegal(leftTop.getX(), leftTop.getY() - 1)) {
                break;
            }
            leftTop.decreaseY();
        }
        while (rightBottom.getX() - center.getX() < radius) {
            if (!blockResource.isGridLegal(rightBottom.getX() + 1, rightBottom.getY())) {
                break;
            }
            rightBottom.plusX();
        }
        while (rightBottom.getY() - center.getY() < radius) {
            if (!blockResource.isGridLegal(rightBottom.getX(), rightBottom.getY() + 1)) {
                break;
            }
            rightBottom.plusY();
        }
    }

}
