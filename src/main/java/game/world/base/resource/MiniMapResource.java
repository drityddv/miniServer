package game.world.base.resource;

import resource.anno.MiniResource;

/**
 * 地图资源文件 阻挡点资源在MapBlockResource
 *
 * @author : ddv
 * @since : 2019/7/2 下午8:53
 */
@MiniResource
public class MiniMapResource {

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
     * 地图组id
     */
    private int groupId;

    /**
     * 阻挡点配置表id
     */
    private int mapDataConfigId;

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

    public int getGroupId() {
        return groupId;
    }

    public int getMapDataConfigId() {
        return mapDataConfigId;
    }

}
