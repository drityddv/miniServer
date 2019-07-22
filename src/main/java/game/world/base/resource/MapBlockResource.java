package game.world.base.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<Grid, List<BroadcastCenter>> aoiModelMap;
    private String aoiPointString;

    @Init
    public void init() {
        analysisMapData();
        analysisAoiPoint();
    }

    private void analysisAoiPoint() {
        aoiModelMap = new ConcurrentHashMap<>();
        String[] data = aoiPointString.split(",");
        for (String point : data) {
            String[] split = point.split(":");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            BroadcastCenter broadcastCenter = new BroadcastCenter(x, y);
            init(broadcastCenter);
        }
    }

    private void init(BroadcastCenter broadcastCenter) {
        init2(broadcastCenter, broadcastCenter.getPointX(), broadcastCenter.getPointY());
        init2(broadcastCenter, broadcastCenter.getPointX(), broadcastCenter.getPointY() + 1);
        init2(broadcastCenter, broadcastCenter.getPointX(), broadcastCenter.getPointY() - 1);
        init2(broadcastCenter, broadcastCenter.getPointX() + 1, broadcastCenter.getPointY());
        init2(broadcastCenter, broadcastCenter.getPointX() + 1, broadcastCenter.getPointY() - 1);
        init2(broadcastCenter, broadcastCenter.getPointX() + 1, broadcastCenter.getPointY() + 1);

        init2(broadcastCenter, broadcastCenter.getPointX() - 1, broadcastCenter.getPointY() + 1);
        init2(broadcastCenter, broadcastCenter.getPointX() - 1, broadcastCenter.getPointY() - 1);
        init2(broadcastCenter, broadcastCenter.getPointX() - 1, broadcastCenter.getPointY());

    }

    private void init2(BroadcastCenter broadcastCenter, int x, int y) {
        Grid grid = Grid.valueOf(x, y);
        List<BroadcastCenter> broadcastCenters = aoiModelMap.get(grid);
        if (broadcastCenters == null) {
            broadcastCenters = new ArrayList<>();
        }
        broadcastCenters.add(broadcastCenter);
        aoiModelMap.put(grid, broadcastCenters);
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

    public Map<Grid, List<BroadcastCenter>> getAoiModelMap() {
        Map<Grid, List<BroadcastCenter>> temp = new ConcurrentHashMap<>();
        aoiModelMap.forEach((grid, centers) -> {
            List<BroadcastCenter> broadcastCenterList = new ArrayList<>();
            centers.forEach(resourceCenter -> broadcastCenterList.add(BroadcastCenter.valueOf(resourceCenter)));
            temp.put(Grid.valueOf(grid), broadcastCenterList);
        });
        return temp;
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

}
