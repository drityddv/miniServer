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

    private transient final Object lock = new Object();
    /**
     * 标志行会是否解散
     */
    private volatile boolean dismiss = false;
    private long allianceId;
    private volatile long chairmanId;
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
        alliance.addAdmin(chairman.getPlayerId());
        alliance.addMember(chairman.getPlayerId());

        for (OperationType operationType : OperationType.values()) {
            alliance.applicationMap.put(operationType, new ConcurrentHashMap<>());
        }
        return alliance;
    }

    public void addMember(long playerId) {
        memberSet.add(playerId);
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

    public void setChairmanId(long chairmanId) {
        this.chairmanId = chairmanId;
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

    public void forceLeave(Player player) {
        applicationMap.values().forEach(map -> {
            BaseAllianceApplication application = map.get(player.getPlayerId());
            if (application != null) {
                application.setExpired(true);
                map.remove(application.getPlayerId());
            }
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
        this.dismiss = true;
        memberSet.forEach(playerId -> {
            SpringContext.getPlayerService().getPlayer(playerId).leaveAlliance(allianceId);
        });
        memberSet.clear();
        adminMember.clear();
    }

    public void addAdmin(long targetMemberId) {
        adminMember.add(targetMemberId);
    }

    public boolean isDismiss() {
        return dismiss;
    }

    public Set<Long> getAdminMember() {
        return adminMember;
    }

    public void removeAdmin(long adminId) {
        adminMember.remove(adminId);
    }
}
