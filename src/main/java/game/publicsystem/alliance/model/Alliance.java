package game.publicsystem.alliance.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    // 申请 增加 删除 临界资源锁用到的锁 每个行会只有一个
    private final Object lock = new Object();
    private long allianceId;
    // 会长
    private long chairmanId;
    private String allianceName;
    // 临界资源操作锁
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

    public boolean addLock(long playerId) {
        synchronized (lock) {
            if (memberLocks.containsKey(playerId)) {
                return false;
            }
            return true;
        }
    }

    public boolean removeLock(long playerId) {
        synchronized (lock) {
            if (!memberLocks.containsKey(playerId)) {
                return false;
            }
            memberLocks.remove(playerId);
            return true;
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

    // 强制成员离开
    public void forceLeave(Player player) {
        synchronized (getLock(player.getPlayerId())) {
            if (!memberSet.contains(player.getPlayerId())) {
                return;
            }
            applicationMap.values().forEach(map -> {
                BaseAllianceApplication application = map.get(player.getPlayerId());
                if (application == null) {
                    return;
                }
                application.setExpired(true);
                map.remove(application.getPlayerId());
            });

            removeLock(player.getPlayerId());
            adminMember.remove(player.getPlayerId());
            memberSet.remove(player.getPlayerId());
            // clear other alliance jobs if exist
            // 这种情况 就不用管 player的信息了
            boolean success = player.changeAllianceId(allianceId, true);
        }
    }

    public boolean isMemberAdmin(long playerId) {
        return adminMember.contains(playerId);
    }

    public boolean isMember(long targetPlayerId) {
        return memberSet.contains(targetPlayerId);
    }

    public Object getLock(long playerId) {
        synchronized (lock) {
            return memberLocks.get(playerId);
        }
    }

    public Object getOrCreateLock(long playerId) {
        synchronized (lock) {
            if (!memberLocks.containsKey(playerId)) {
                memberLocks.put(playerId, new Object());
            }
            return memberLocks.get(playerId);
        }
    }

    public Map<Long, Object> getMemberLocks() {
        return memberLocks;
    }
}
