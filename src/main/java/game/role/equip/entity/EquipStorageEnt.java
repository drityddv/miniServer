package game.role.equip.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.role.equip.model.EquipStorage;
import utils.ProtoStuffUtil;

/**
 * 玩家装备栏
 *
 * @author : ddv
 * @since : 2019/6/28 下午5:36
 */
@Entity(name = "equipStorage")
public class EquipStorageEnt extends AbstractEntity<Long> {

    @Transient
    private EquipStorage equipStorage;

    @Lob
    @Column(columnDefinition = "blob comment '玩家装备栏数据' ")
    private byte[] equipStorageData;

    @Id
    @Column(columnDefinition = "bigint comment '玩家Id' ", nullable = false)
    private long playerId;

    public static EquipStorageEnt valueOf(long playerId) {
        EquipStorageEnt ent = new EquipStorageEnt();
        ent.setPlayerId(playerId);
        ent.equipStorage = EquipStorage.valueOf();
        return ent;
    }

    @Override
    public void doSerialize() {
        if (equipStorage != null) {
            equipStorageData = ProtoStuffUtil.serialize(equipStorage);
        }
    }

    @Override
    public void doDeserialize() {
        if (equipStorageData != null) {
            equipStorage = ProtoStuffUtil.deserialize(equipStorageData, EquipStorage.class);
        }
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public EquipStorage getEquipStorage() {
        return equipStorage;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
