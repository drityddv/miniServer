package game.world.base.resource;

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
    private String mapDataString;
    private int[][] blockData;

    @Init
    public void init() {
        analysisMapData();
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

    public int[][] getBlockData() {
        return blockData;
    }
}
