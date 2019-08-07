package game.role.player.model;

import game.publicsystem.alliance.constant.AllianceConst;

/**
 * 玩家行会相关信息
 *
 * @author : ddv
 * @since : 2019/8/6 12:17 PM
 */

public class PlayerAllianceInfo {
    private final Object lock = new Object();
    private volatile long allianceId;

    public static PlayerAllianceInfo valueOf() {
        return new PlayerAllianceInfo();
    }

    /**
     * 目前只有走流程的进出公会 创立公会 才会有管理员改这个字段
     *
     * @param allianceId
     * @param leave
     *            这个字段是专门用来离开行会的 需要跳过allianceId验证
     * @return
     */
    public boolean changeAllianceId(long allianceId, boolean leave) {
        synchronized (lock) {
            if (isInAlliance() && !leave) {
                return false;
            }
            this.allianceId = allianceId;
            return true;
        }
    }

    public long getAllianceId() {
        return allianceId;
    }

    public boolean isInAlliance() {
        return allianceId != AllianceConst.EMPTY_ALLIANCE_ID;
    }
}
