package game.user.mapinfo.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.map.base.AbstractPlayerMapInfo;
import game.map.constant.MapGroupType;
import game.user.mapinfo.model.PlayerMapInfo;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/7/2 下午5:11
 */
@Entity(name = "mapInfo")
public class MapInfoEnt extends AbstractEntity<String> {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '玩家账户id' ", nullable = false)
    private String accountId;

    @Transient
    private PlayerMapInfo playerMapInfo;

    @Lob
    @Column(columnDefinition = "blob comment '玩家地图数据' ")
    private byte[] mapData;

    @Column(columnDefinition = "int default 0 comment '玩家当前所在地图'", nullable = true)
    private volatile int currentMapId;

    @Column(columnDefinition = "bigint default 0 comment '玩家当前所在地图分线场景'", nullable = true)
    private volatile long currentSceneId;

    @Column(columnDefinition = "int default 0 comment '玩家上一张地图'", nullable = true)
    private volatile int lastMapId;

    public static MapInfoEnt valueOf(String accountId) {
        MapInfoEnt ent = new MapInfoEnt();
        ent.accountId = accountId;
        ent.playerMapInfo = PlayerMapInfo.valueOf();
        return ent;
    }

    public AbstractPlayerMapInfo getOrCreateMapInfo(MapGroupType type) {
        return playerMapInfo.getOrCreateMapInfo(type);
    }

    @Override
    public void doSerialize() {
        if (playerMapInfo != null) {
            mapData = ProtoStuffUtil.serialize(playerMapInfo);
        }
    }

    @Override
    public void doDeserialize() {
        if (mapData != null) {
            playerMapInfo = ProtoStuffUtil.deserialize(mapData, PlayerMapInfo.class);
        }
    }

    public int getCurrentMapId() {
        return currentMapId;
    }

    public void setCurrentMapId(int currentMapId) {
        this.currentMapId = currentMapId;
    }

    @Override
    public String getId() {
        return accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public PlayerMapInfo getPlayerMapInfo() {
        return playerMapInfo;
    }

    public int getLastMapId() {
        return lastMapId;
    }

    public void setLastMapId(int lastMapId) {
        this.lastMapId = lastMapId;
    }

    public long getCurrentSceneId() {
        return currentSceneId;
    }

    public void setCurrentSceneId(long currentSceneId) {
        this.currentSceneId = currentSceneId;
    }
}
