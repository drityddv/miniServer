package game.publicsystem.rank.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.publicsystem.rank.model.ServerRank;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/8/5 4:48 PM
 */
@Entity(name = "rank")
public class ServerRankEnt extends AbstractEntity<Long> {

    @Transient
    private ServerRank serverRank;

    @Lob
    @Column(columnDefinition = "blob comment '服务器rank数据' ")
    private byte[] rankInfoData;

    @Id
    @Column(columnDefinition = "bigint comment '服务器id' ", nullable = false)
    private long serverId;

    public static ServerRankEnt valueOf(long serverId) {
        ServerRankEnt ent = new ServerRankEnt();
        ent.serverId = serverId;
        ent.serverRank = ServerRank.valueOf();
        return ent;
    }

    @Override
    public void doSerialize() {
        if (serverRank != null) {
            rankInfoData = ProtoStuffUtil.serialize(serverRank);
        }
    }

    @Override
    public void doDeserialize() {
        if (rankInfoData != null) {
            serverRank = ProtoStuffUtil.deserialize(rankInfoData, ServerRank.class);
        }
    }

    @Override
    public Long getId() {
        return serverId;
    }

    public ServerRank getServerRank() {
        return serverRank;
    }
}
