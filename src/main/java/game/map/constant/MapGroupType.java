package game.map.constant;

import java.util.HashMap;
import java.util.Map;

import game.map.base.AbstractPlayerMapInfo;
import game.world.instance.groupInstance.model.GroupInstancePlayerMapInfo;
import game.world.instance.singleIntance.model.SingleInstancePlayerMapInfo;
import game.world.mainCity.model.MainCityPlayerMapInfo;
import game.world.neutral.neutralMap.model.NeutralPlayerMapInfo;

/**
 * 地图分类
 *
 * @author : ddv
 * @since : 2019/7/2 下午10:02
 */

public enum MapGroupType {
    /**
     * 空组
     */
    EMPTY_GROUP(0, null),

    /**
     * 中立地图
     */
    NEUTRAL_MAP(1, new NeutralPlayerMapInfo()) {
        @Override
        public boolean isNeedCd() {
            return false;
        }
    },
    /**
     * 主城 保底地图 没有进入限制 用来接盘玩家进图失败的异常情况
     */
    MAIN_CITY(2, new MainCityPlayerMapInfo()) {
        @Override
        public boolean isNeedCd() {
            return false;
        }

    },
    /**
     * 单人副本
     */
    SINGLE_INSTANCE(3, new SingleInstancePlayerMapInfo()) {

    },
    /**
     * 多人副本
     */
    GROUP_INSTANCE(4, new GroupInstancePlayerMapInfo()) {

    },;
    private static final Map<Class<? extends AbstractPlayerMapInfo>, MapGroupType> CLASS_TO_TYPE = new HashMap<>();
    private static final Map<Integer, MapGroupType> GROUP_ID_TO_TYPE = new HashMap<>();

    static {
        for (MapGroupType type : MapGroupType.values()) {
            if (type.mapInfo != null) {
                CLASS_TO_TYPE.put(type.mapInfo.getClass(), type);
            }
            GROUP_ID_TO_TYPE.put(type.getGroupId(), type);
        }
    }

    /**
     * 地图所属组id
     */
    private int groupId;
    private AbstractPlayerMapInfo mapInfo;

    MapGroupType(int groupId, AbstractPlayerMapInfo mapInfo) {
        this.groupId = groupId;
        this.mapInfo = mapInfo;
    }

    public static MapGroupType getTypeByMapInfo(AbstractPlayerMapInfo mapInfo) {
        return CLASS_TO_TYPE.get(mapInfo);
    }

    public static MapGroupType getTypeByGroupId(int groupId) {
        return GROUP_ID_TO_TYPE.get(groupId);
    }

    public boolean isNeedCd() {
        return true;
    }

    public int getGroupId() {
        return groupId;
    }

    public AbstractPlayerMapInfo getMapInfo() {
        return mapInfo;
    }

    public AbstractPlayerMapInfo initAndCreateMapInfo() {
        return mapInfo.valueOf();
    }
}
