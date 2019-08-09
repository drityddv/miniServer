package game.role.player.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private Map<Long, Long> inviteMap = new ConcurrentHashMap<>();

    public static PlayerAllianceInfo valueOf() {
        return new PlayerAllianceInfo();
    }

    /**
     * 目前只有走流程的进出公会 创立公会 才会有管理员改这个字段
     *
     * @param allianceId
     * @return
     */
    public boolean changeAllianceId(long allianceId) {
        synchronized (lock) {
            if (isInAlliance()) {
                return false;
            }
            this.allianceId = allianceId;
            return true;
        }
    }

    /**
     *
     * @param currentAllianceId
     * @return 是否需要保存player信息 || 玩家是否离开行会成功
     */
    public boolean leaveAlliance(long currentAllianceId) {
        synchronized (lock) {
            if (this.allianceId != currentAllianceId || this.allianceId == AllianceConst.EMPTY_ALLIANCE_ID) {
                // 说明已经进入了新行会或者已经退出
                return false;
            }
            this.allianceId = AllianceConst.EMPTY_ALLIANCE_ID;
            return true;
        }
    }

    public long getAllianceId() {
        return allianceId;
    }

    // 是否在行会中
    public boolean isInAlliance() {
        return allianceId != AllianceConst.EMPTY_ALLIANCE_ID;
    }

    public void addInvite(long memberId, long allianceId) {
        inviteMap.put(memberId, allianceId);
    }

    public void removeInvite(long inviteId) {
        inviteMap.remove(inviteId);
    }

    public Long getInvite(long inviteId) {
        return inviteMap.get(inviteId);
    }

    public boolean containsInvite(long inviteId) {
        return inviteMap.containsKey(inviteId);
    }

    public Map<Long, Long> getInviteMap() {
        return inviteMap;
    }
}
