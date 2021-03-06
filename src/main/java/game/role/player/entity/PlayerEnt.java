package game.role.player.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.role.player.model.Player;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/5/14 下午5:14
 */
@Entity(name = "player")
public class PlayerEnt extends AbstractEntity<String> {

    @Transient
    private Player player;

    @Lob
    @Column(columnDefinition = "blob comment '玩家信息' ")
    private byte[] playerData;

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号Id' ", nullable = false)
    private String accountId;

    public static PlayerEnt valueOf(String accountId) {
        PlayerEnt playerEnt = new PlayerEnt();
        playerEnt.accountId = accountId;
        playerEnt.player = Player.valueOf(accountId);
        return playerEnt;
    }

    @Override
    public void doSerialize() {
        if (this.player != null) {
            this.playerData = ProtoStuffUtil.serialize(player);
        }
    }

    @Override
    public void doDeserialize() {
        if (this.playerData != null) {
            this.player = ProtoStuffUtil.deserialize(this.playerData, Player.class);
            this.player.getAttributeContainer().setOwner(this.player);
        }
    }

    @Override
    public String getId() {
        return accountId;
    }

    // get and set
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
