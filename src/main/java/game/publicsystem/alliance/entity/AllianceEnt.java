package game.publicsystem.alliance.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import utils.ProtoStuffUtil;

/**
 * 所有工会数据存在服务器上
 *
 * @author : ddv
 * @since : 2019/8/4 1:33 AM
 */
@Entity(name = "alliance")
public class AllianceEnt extends AbstractEntity<Long> {

    @Transient
    private ServerAllianceInfo allianceInfo;

    @Lob
    @Column(columnDefinition = "blob comment '服务器工会数据' ")
    private byte[] allianceInfoData;

    @Id
    @Column(columnDefinition = "bigint comment '服务器id' ", nullable = false)
    private long serverId;

    public static AllianceEnt valueOf(long serverId) {
        AllianceEnt ent = new AllianceEnt();
        ent.serverId = serverId;
        ent.allianceInfo = ServerAllianceInfo.valueOf(serverId);
        return ent;
    }

    @Override
    public void doSerialize() {
        if (allianceInfo != null) {
            allianceInfoData = ProtoStuffUtil.serialize(allianceInfo);
        }
    }

    @Override
    public void doDeserialize() {
        if (allianceInfoData != null) {
            allianceInfo = ProtoStuffUtil.deserialize(allianceInfoData, ServerAllianceInfo.class);
        }
    }

    @Override
    public Long getId() {
        return serverId;
    }

    public ServerAllianceInfo getAllianceInfo() {
        return allianceInfo;
    }

    public long getServerId() {
        return serverId;
    }
}
