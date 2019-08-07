package game.publicsystem.alliance.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.MessageEnum;
import game.publicsystem.alliance.constant.AllianceConst;
import game.publicsystem.alliance.constant.OperationType;
import game.publicsystem.alliance.entity.AllianceEnt;
import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.publicsystem.alliance.model.application.BaseAllianceApplication;
import game.publicsystem.alliance.model.application.JoinApplication;
import game.publicsystem.alliance.model.application.LeaveApplication;
import game.publicsystem.alliance.packet.SM_ServerAllianceVo;
import game.role.player.model.Player;
import game.role.player.model.PlayerAllianceInfo;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * 公会接口
 *
 * @author : ddv
 * @since : 2019/8/3 11:11 PM
 */
@Component
public class AllianceService implements IAllianceService {

    private static final Logger logger = LoggerFactory.getLogger(AllianceService.class);

    @Autowired
    private AllianceManager allianceManager;

    @Override
    public void init() {
        ServerAllianceInfo allianceInfo = getServerAllianceInfo();
        allianceInfo.init();
    }

    @Override
    public void createAlliance(Player player, String allianceName) {
        PlayerAllianceInfo playerPlayerAllianceInfo = player.getPlayerAllianceInfo();

        // 必须是野人才可以创建公会
        if (playerPlayerAllianceInfo.isInAlliance()) {
            logger.warn("玩家[{}]创建公会失败,玩家是公会[{}]的成员!", player.getAccountId(), playerPlayerAllianceInfo.getAllianceId());
            return;
        }

        Alliance alliance = Alliance.valueOf(player, allianceName);
        boolean success = player.changeAllianceId(alliance.getAllianceId(), false);
        // inCase 创建公会之后 可能遗留的加入申请被处理了 所以要再check
        if (!success) {
            logger.warn("玩家[{}]创建公会失败,玩家已经是公会[{}]的成员了!", player.getAccountId(),
                playerPlayerAllianceInfo.getAllianceId());
            return;
        }

        AllianceEnt allianceEnt = getAllianceEnt();
        allianceEnt.getAllianceInfo().addAlliance(alliance);
        saveServerAllianceInfo();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void kickMember(Player player, long targetPlayerId) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();

        if (playerAllianceInfo.getAllianceId() == AllianceConst.EMPTY_ALLIANCE_ID) {
            logger.warn("玩家[{}]踢人失败,玩家不属于任何公会", player.getAccountId());
            return;
        }

        Alliance alliance = getAlliance(playerAllianceInfo.getAllianceId());

        if (!alliance.isMemberAdmin(player.getPlayerId())) {
            logger.warn("玩家[{}]踢人失败,玩家不是管理员", player.getAccountId());
            return;
        }

        if (alliance.isMemberAdmin(targetPlayerId)) {
            logger.warn("玩家[{}]踢人[{}]失败,目标也是管理员", player.getAccountId(), targetPlayerId);
            return;
        }

        if (!alliance.isMember(targetPlayerId)) {
            logger.warn("玩家[{}]踢人[{}]失败,目标不存在于公会之中", player.getAccountId(), targetPlayerId);
            return;
        }
        alliance.forceLeave(SpringContext.getPlayerService().getPlayer(targetPlayerId));
    }

    @Override
    public Alliance getAlliance(long allianceId) {
        return getServerAllianceInfo().getAlliance(allianceId);
    }

    @Override
    public ServerAllianceInfo getAllianceInfo(Player player) {
        ServerAllianceInfo allianceInfo = getAllianceEnt().getAllianceInfo();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(allianceInfo));
        return allianceInfo;
    }

    @Override
    public void joinApplication(Player player, long allianceId) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        // 野人才能申请
        if (playerAllianceInfo.isInAlliance()) {
            logger.warn("玩家[{}] 申请加入公会失败,玩家已经存在于公会[{}]之中!", player.getAccountId(), playerAllianceInfo.getAllianceId());
            return;
        }

        Alliance alliance = getAlliance(allianceId);
        if (alliance == null) {
            logger.warn("玩家[{}] 申请加入公会[{}]失败,该公会不存在", player.getAccountId(), allianceId);
            return;
        }

        List<OperationType> conflictTypes = OperationType.Join_Alliance.getConflictTypes();
        boolean legality = alliance.isOperationLegality(conflictTypes, player);

        if (legality) {
            Object lock = alliance.getOrCreateLock(player.getPlayerId());
            synchronized (lock) {
                alliance.addApplication(JoinApplication.valueOf(player, alliance.getAllianceId()));
            }
        } else {
            PacketUtil.send(player, MessageEnum.Conflict_Application);
            return;
        }

        saveServerAllianceInfo();
        sendAllianceInfo(player);
    }

    @Override
    public void sendAllianceInfo(Player player) {
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void leaveApplication(Player player, boolean force) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        long allianceId = playerAllianceInfo.getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        // 提交离开申请 此时可能已经不在了
        if (!playerAllianceInfo.isInAlliance()) {
            return;
        }

        // 会长暂时不允许离开
        if (alliance.getChairmanId() == player.getPlayerId()) {
            return;
        }

        if (force) {
            alliance.forceLeave(player);
        } else {
            // 是否有冲突的申请 这个判断不上锁也没毛病
            List<OperationType> conflictTypes = OperationType.Join_Alliance.getConflictTypes();
            boolean legality = alliance.isOperationLegality(conflictTypes, player);
            if (legality) {
                synchronized (alliance.getOrCreateLock(player.getPlayerId())) {
                    alliance.addApplication(LeaveApplication.valueOf(player));
                }
            } else {
                PacketUtil.send(player, MessageEnum.Conflict_Application);
                return;
            }
        }
        saveServerAllianceInfo();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void handlerApplication(Player player, int operationTypeId, long applicationId, boolean agreed) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        OperationType operationType = OperationType.getById(operationTypeId);
        if (operationType == null) {
            logger.warn("玩家处理申请[{}]失败,参数[{}]有误", player.getPlayerId(), operationTypeId);
            return;
        }

        Alliance alliance = getAlliance(playerAllianceInfo.getAllianceId());
        // 防止恶意发包引发null point
        if (alliance == null) {
            logger.warn("玩家[{}]处理申请失败,该玩家无公会", player.getPlayerId());
            return;
        }
        // 不是管理员
        if (!alliance.isMemberAdmin(player.getPlayerId())) {
            logger.warn("玩家[{}]处理申请失败,玩家不是管理员", player.getPlayerId());
            return;
        }
        // 核查申请表时效性
        BaseAllianceApplication application = alliance.getApplicationMap().get(operationType).get(applicationId);
        if (application == null || application.isExpired()) {
            logger.warn("玩家[{}]处理申请失败,申请不存在或者过期", player.getPlayerId());
            return;
        }
        Object lock = alliance.getLock(application.getPlayerId());
        if (lock == null) {
            logger.warn("玩家[{}]处理申请失败,申请对象[{}]已经离开行会", player.getPlayerId(), application.getPlayerId());
            return;
        }

        synchronized (lock) {
            if (application.handler(agreed)) {
                saveServerAllianceInfo();
            }
        }
    }

    private void saveAllianceEnt(AllianceEnt allianceEnt) {
        allianceManager.save(allianceEnt);
    }

    private AllianceEnt getAllianceEnt() {
        return allianceManager.loadOrCreate(AllianceConst.SERVER_ID);
    }

    private ServerAllianceInfo getServerAllianceInfo() {
        return getAllianceEnt().getAllianceInfo();
    }

    private void saveServerAllianceInfo() {
        saveAllianceEnt(getAllianceEnt());
    }

}
