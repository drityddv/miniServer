package game.user.pack.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.user.pack.model.Pack;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/5/31 下午5:30
 */
@Entity(name = "pack")
public class PackEnt extends AbstractEntity<Long> {

    @Transient
    private Pack pack;
    @Lob
    @Column(columnDefinition = "blob comment '玩家背包数据' ")
    private byte[] packData;

    @Id
    @Column(columnDefinition = "bigint comment '玩家Id' ", nullable = false)
    private long playerId;

    public static PackEnt valueOf(long playerId) {
        PackEnt ent = new PackEnt();
        ent.setPlayerId(playerId);
        ent.setPack(Pack.valueOf());
        return ent;
    }

    @Override
    public void doSerialize() {
        if (pack != null) {
            packData = ProtoStuffUtil.serialize(pack);
        }
    }

    @Override
    public void doDeserialize() {
        if (packData != null) {
            pack = ProtoStuffUtil.deserialize(packData, Pack.class);
        }
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
