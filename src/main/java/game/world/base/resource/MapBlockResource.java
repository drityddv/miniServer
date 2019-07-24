package game.world.base.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.map.base.BroadcastCenter;
import game.map.model.Grid;
import resource.anno.Init;
import resource.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/7/8 下午12:31
 */

@MiniResource
public class MapBlockResource {
    private int configId;
    private int x;
    private int y;
    private int[][] blockData;
    private String mapDataString;
    private Map<Grid, BroadcastCenter> aoiModelMap;
    private String aoiPointString;

    @Init
    public void init() {
        analysisMapData();
        analysisAoiPoint();
    }

    private void analysisAoiPoint() {
        initAoi();
    }

    private void analysisMapData() {
        String[] data = mapDataString.split(",");
        int index = 0;
        blockData = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                blockData[i][j] = JodaUtil.convertFromString(Integer.class, data[index++]);
            }
        }
    }

    private void initAoi() {
        aoiModelMap = new HashMap<>();
        // 默认广播范围边长
        int defaultSize = 4;
        Map<Grid, Boolean> occupyMap = new HashMap<>();
        List<BroadcastCenter> centerList = new ArrayList<>();

        int stepY = (y - 1) % defaultSize > 0 ? y / defaultSize + 1 : y / defaultSize;
        int stepX = (x - 1) % defaultSize > 0 ? x / defaultSize + 1 : x / defaultSize;

        int realY = 0;
        for (int i = 0; i < stepY; i++) {
            int incrementY = realY + defaultSize > y - 1 ? y - 1 - realY : defaultSize;
            realY += incrementY;
            int realX = 0;
            for (int j = 0; j < stepX; j++) {
                int incrementX = realX + defaultSize > x - 1 ? x - 1 - realX : defaultSize;
                realX += incrementX;

                int centerX = realX - (incrementX / 2);
                int centerY = realY - (incrementY / 2);
                Grid center = Grid.valueOf(centerX, centerY);
                occupyMap.put(center, true);
                BroadcastCenter broadcastCenter = new BroadcastCenter(centerX, centerY);
                broadcastCenter.init(center);

                for (int startY = realY - incrementY; startY <= realY; startY++) {
                    for (int startX = realX - incrementX; startX <= realX; startX++) {
                        Grid grid = Grid.valueOf(startX, startY);
                        if (!occupyMap.containsKey(grid)) {
                            broadcastCenter.init(grid);
                            occupyMap.put(grid, true);
                        }
                    }
                }

                centerList.add(broadcastCenter);
            }
        }

        centerList.forEach(broadcastCenter -> {
            broadcastCenter.getUnitMap().keySet().forEach(grid -> {
                aoiModelMap.put(grid, broadcastCenter);
            });
        });
    }

    // get
    public int getConfigId() {
        return configId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getBlockData() {
        return blockData;
    }

    // methods
    public boolean isGridLegal(Grid grid) {
        return aoiModelMap.containsKey(grid);
    }

    public boolean isGridLegal(int x, int y) {
        return aoiModelMap.containsKey(Grid.valueOf(x, y));
    }

    public Map<Grid, BroadcastCenter> getAoiModelMap() {
        Map<Grid, BroadcastCenter> copy = new HashMap<>();
        aoiModelMap.forEach((grid, broadcastCenter) -> {
            BroadcastCenter center = BroadcastCenter.valueOf(broadcastCenter);
            center.getUnitMap().keySet().forEach(grid1 -> {
                copy.put(grid1, center);
            });

        });
        return copy;
    }

}
