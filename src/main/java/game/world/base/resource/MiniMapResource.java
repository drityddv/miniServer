package game.world.base.resource;

import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/7/2 下午8:53
 */
@MiniResource
public class MiniMapResource {

    // private static final Logger logger = LoggerFactory.getLogger(MiniMapResource.class);
    /**
     * id
     *
     */
    private int mapId;
    /**
     * 地图名
     */
    private String name;
    /**
     * 地图长宽与阻挡点
     */
    private int mapX;
    private int mapY;
    private int[][] mapData;
    private String mapDataString;
    /**
     * 进入最大等级
     */
    private int maxLevel;
    /**
     * 进入最小等级
     */
    private int minLevel;

    /**
     * 出生坐标点
     */
    private int bornX;
    private int bornY;
    /**
     * 地图类型
     */
    private int mapType;
    /**
     * 地图组id
     */
    private int groupId;

    @Init
    public void init() {
        analysisMapData();
    }

    private void analysisMapData() {
        String[] data = mapDataString.split(",");
        int index = 0;
        mapData = new int[mapX][mapY];
        for (int i = 0; i < mapX; i++) {
            for (int j = 0; j < mapX; j++) {
                mapData[i][j] = JodaUtil.convertFromString(Integer.class, data[index++]);
            }
        }
    }

    // get and set
    public int getMapId() {
        return mapId;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getBornX() {
        return bornX;
    }

    public int getBornY() {
        return bornY;
    }

    public int getMapType() {
        return mapType;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public int getGroupId() {
        return groupId;
    }
}
