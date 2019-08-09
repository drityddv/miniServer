package game.publicsystem.alliance.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import game.publicsystem.alliance.constant.OperationType;
import game.publicsystem.alliance.model.application.BaseAllianceApplication;
import game.role.player.model.Player;
import io.netty.util.internal.ConcurrentSet;
import spring.SpringContext;
import utils.snow.IdUtil;

/**
 * @author : ddv
 * @since : 2019/8/3 3:43 PM
 */

public class Alliance {

    private final Object lock = new Object();
    private long allianceId;
    private long chairmanId;
    private String allianceName;
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
        synchronized (lock) {
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
        applicationMap.values().forEach(map -> {
            BaseAllianceApplication application = map.get(player.getPlayerId());
            application.setExpired(true);
            map.remove(application.getPlayerId());
        });

        adminMember.remove(player.getPlayerId());
        memberSet.remove(player.getPlayerId());
        player.leaveAlliance(allianceId);
    }

    public boolean isMemberAdmin(long playerId) {
        return adminMember.contains(playerId);
    }

    public boolean isMember(long targetPlayerId) {
        return memberSet.contains(targetPlayerId);
    }

    public Object getLock() {
        return lock;
    }

    // 解散行会
    public void dismiss() {
        memberSet.forEach(playerId -> {
            SpringContext.getPlayerService().getPlayer(playerId).leaveAlliance(allianceId);
        });
        memberSet.clear();
        adminMember.clear();
    }

    public void addAdmin(long targetMemberId) {
        adminMember.add(targetMemberId);
    }
}
