package game.miniMap.constant;

import java.util.HashMap;
import java.util.Map;

import game.miniMap.base.AbstractMapInfo;
import game.world.areanMap.ArenaMapInfo;
import game.world.neutral.neutralMap.model.NeutralMapInfo;

/**
 * 地图分类
 *
 * @author : ddv
 * @since : 2019/7/2 下午10:02
 */

public enum MapGroupType {
    /**
     * 空副本
     */
    EMPTY_GROUP(0, null),

    /**
     * 野外地图
     */
    ARENA_GROUP(1, new ArenaMapInfo()) {
        @Override
        public boolean needLogPvpResult() {
            return false;
        }
    },
    /**
     * 中立地图
     */
    NEUTRAL_MAP(2, new NeutralMapInfo()) {
        @Override
        public boolean isNeedCd() {
            return false;
        }
    };
    private static final Map<Class<? extends AbstractMapInfo>, MapGroupType> CLASS_TO_TYPE = new HashMap<>();
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
    private AbstractMapInfo mapInfo;

    MapGroupType(int groupId, AbstractMapInfo mapInfo) {
        this.groupId = groupId;
        this.mapInfo = mapInfo;
    }

    public static MapGroupType getTypeByMapInfo(AbstractMapInfo mapInfo) {
        return CLASS_TO_TYPE.get(mapInfo);
    }

    public static MapGroupType getTypeByGroupId(int groupId) {
        return GROUP_ID_TO_TYPE.get(groupId);
    }

    // 进入野外如果没有cd 玩家发包疯狂发包刷怪
    public boolean isNeedCd() {
        return true;
    }

    // 是否需要记录战报 打怪之类的副本不需要
    public boolean needLogPvpResult() {
        return true;
    }

    // 是否允许副本地图内切图
    public boolean isAllowChangeMapInMap() {
        return false;
    }

    public int getGroupId() {
        return groupId;
    }

    public AbstractMapInfo getMapInfo() {
        return mapInfo;
    }

    public AbstractMapInfo initAndCreateMapInfo() {
        return mapInfo.valueOf();
    }
}
