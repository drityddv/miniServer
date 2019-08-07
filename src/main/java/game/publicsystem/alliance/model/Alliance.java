package game.publicsystem.alliance.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.publicsystem.alliance.constant.AllianceConst;
import game.publicsystem.alliance.constant.OperationType;
import game.publicsystem.alliance.model.application.BaseAllianceApplication;
import game.role.player.model.Player;
import io.netty.util.internal.ConcurrentSet;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/8/3 3:43 PM
 */

public class Alliance {
    private final Object adminLock = new Object();
    private final Object memberLock = new Object();
    private long allianceId;
    // 会长
    private long chairmanId;
    private String allianceName;
    // 成员操作锁
    private Map<Long, Object> memberLocks = new ConcurrentHashMap<>();
    private Set<Long> adminMember = new ConcurrentSet<>();
    private Set<Long> memberSet = new ConcurrentSet<>();
    // 申请列表
    private Map<OperationType, Map<Long, BaseAllianceApplication>> applicationMap = new ConcurrentHashMap<>();

    public Alliance() {}

    public static Alliance valueOf(Player chairman, String allianceName) {
        Alliance alliance = new Alliance();
        alliance.allianceId = IdUtil.getLongId();
        alliance.chairmanId = chairman.getPlayerId();
        alliance.allianceName = allianceName;
        alliance.addAdmin(chairman);
        alliance.addMember(chairman);

        for (OperationType operationType : OperationType.values()) {
            alliance.applicationMap.put(operationType, new ConcurrentHashMap<>());
        }
        return alliance;
    }

    /**
     * 添加管理员
     *
     * @param playerId
     * @return
     */
    public void addAdmin(Long playerId) {
        synchronized (getLock(playerId)) {
            if (adminMember.contains(playerId)) {
                return;
            }
            adminMember.add(playerId);
        }
    }

    /**
     * 添加成员
     *
     * @param playerId
     * @return
     */
    public void addMember(long playerId) {
        if (memberSet.contains(playerId)) {
            return;
        }
        memberLocks.put(playerId, new Object());
        memberSet.add(playerId);
    }

    private void addAdmin(Player admin) {
        adminMember.add(admin.getPlayerId());
    }

    public void addMember(Player member) {
        memberSet.add(member.getPlayerId());
    }

    public long getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(long allianceId) {
        this.allianceId = allianceId;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public long getChairmanId() {
        return chairmanId;
    }

    public Set<Long> getMemberSet() {
        return memberSet;
    }

    public Map<OperationType, Map<Long, BaseAllianceApplication>> getApplicationMap() {
        return applicationMap;
    }

    public void addApplication(BaseAllianceApplication application) {
        applicationMap.get(application.getOperationType()).put(application.getPlayerId(), application);
    }

    public boolean isOperationLegality(List<OperationType> conflictTypes, Player player) {
        for (OperationType conflictType : conflictTypes) {
            if (applicationMap.get(conflictType).get(player.getPlayerId()) != null) {
                return false;
            }
        }
        return true;
    }

    // 队员强退 干掉所有申请
    public void forceLeave(Player player) {
        applicationMap.values().forEach(map -> {
            BaseAllianceApplication application = map.get(player.getPlayerId());
            application.setExpired(true);
            map.remove(application.getPlayerId());
        });

        adminMember.remove(player.getPlayerId());
        memberSet.remove(player.getPlayerId());
        // clear other alliance jobs if exist
        // 这种情况 不可能改失败
        player.changeAllianceId(AllianceConst.EMPTY_ALLIANCE_ID, true);
    }

    public boolean isMemberAdmin(long playerId) {
        return adminMember.contains(playerId);
    }

    public boolean isMember(long targetPlayerId) {
        return memberSet.contains(targetPlayerId);
    }

    private Object getLock(long playerId) {
        return memberLocks.get(playerId);
    }
}
