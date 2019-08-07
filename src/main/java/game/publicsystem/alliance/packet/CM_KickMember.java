package game.publicsystem.alliance.packet;

/**
 * @author : ddv
 * @since : 2019/8/6 10:52 PM
 */

public class CM_KickMember {
    // 要踢的人的id
    private long playerId;

    public long getPlayerId() {
        return playerId;
    }
}
