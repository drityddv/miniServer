package game.user.mapinfo.entity;

import javax.persistence.*;

import db.middleware.AbstractEntity;
import game.miniMap.handler.MapGroupType;
import game.user.mapinfo.model.PlayerMapInfo;
import game.world.AbstractMapInfo;
import net.utils.ProtoStuffUtil;

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

    public static MapInfoEnt valueOf(String accountId) {
        MapInfoEnt ent = new MapInfoEnt();
        ent.accountId = accountId;
        ent.playerMapInfo = PlayerMapInfo.valueOf();
        return ent;
    }

    public AbstractMapInfo getOrCreateMapInfo(MapGroupType type) {
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
}
