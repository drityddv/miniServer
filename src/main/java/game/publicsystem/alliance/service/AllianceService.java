package game.publicsystem.alliance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.MessageEnum;
import game.publicsystem.alliance.constant.AllianceConst;
import game.publicsystem.alliance.constant.CheckType;
import game.publicsystem.alliance.constant.OperationType;
import game.publicsystem.alliance.entity.AllianceEnt;
import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.AllianceParam;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.publicsystem.alliance.model.application.BaseAllianceApplication;
import game.publicsystem.alliance.model.application.JoinApplication;
import game.publicsystem.alliance.packet.SM_PlayerAllianceVo;
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
    public void createAlliance(Player player, String allianceName) {
        PlayerAllianceInfo playerPlayerAllianceInfo = player.getPlayerAllianceInfo();

        // 必须是野人才可以创建公会
        if (playerPlayerAllianceInfo.isInAlliance()) {
            logger.warn("玩家[{}]创建公会失败,玩家是公会[{}]的成员!", player.getAccountId(), playerPlayerAllianceInfo.getAllianceId());
            return;
        }

        Alliance alliance = Alliance.valueOf(player, allianceName);
        boolean success = player.changeAllianceId(alliance.getAllianceId());
        // 修改id失败 可能被其他行会同意加入了
        if (!success) {
            logger.warn("玩家[{}]创建公会失败,玩家已经是公会[{}]的成员了!", player.getAccountId(),
                playerPlayerAllianceInfo.getAllianceId());
            return;
        }

        getServerAllianceInfo().addAlliance(alliance);
        saveAlliance();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void pullJoinApplication(Player player, long allianceId) {
        PlayerAllianceInfo allianceInfo = player.getPlayerAllianceInfo();
        // 野人才能申请
        if (allianceInfo.isInAlliance()) {
            logger.warn("玩家[{}] 申请加入公会失败,玩家已经存在于公会[{}]之中!", player.getAccountId(), allianceInfo.getAllianceId());
            return;
        }

        Alliance alliance = getAlliance(allianceId);
        AllianceParam param = AllianceParam.valueOf(player, allianceId);
        checkAllianceCondition(param, AllianceConst.ALLIANCE_LEGAL);

        // 检测是否满提交条件
        boolean legality = alliance.isOperationLegality(OperationType.Join_Alliance.getConflictTypes(), player);
        if (legality) {
            synchronized (alliance.getLock()) {
                checkAllianceCondition(param, AllianceConst.ALLIANCE_LEGAL);
                alliance.addApplication(JoinApplication.valueOf(player, alliance.getAllianceId()));
            }
        } else {
            PacketUtil.send(player, MessageEnum.Conflict_Application);
            return;
        }

        saveAlliance();
        sendAllianceInfo(player);
    }

    @Override
    public void leaveAlliance(Player player) {
        PlayerAllianceInfo allianceInfo = player.getPlayerAllianceInfo();
        long allianceId = allianceInfo.getAllianceId();
        Alliance alliance = getAlliance(allianceId);
        AllianceParam param = AllianceParam.valueOf(player, allianceId);
        checkAllianceCondition(param, AllianceConst.LEAVE_ALLIANCE);

        synchronized (alliance.getLock()) {
            //
            checkAllianceCondition(param, AllianceConst.LEAVE_ALLIANCE);
            alliance.forceLeave(player);
        }

        saveAlliance();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void handlerApplication(Player player, int operationTypeId, long applicationId, boolean agreed) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        long allianceId = playerAllianceInfo.getAllianceId();
        OperationType operationType = OperationType.getById(operationTypeId);
        if (operationType == null) {
            logger.warn("玩家处理申请[{}]失败,参数[{}]有误", player.getPlayerId(), operationTypeId);
            return;
        }

        AllianceParam param = AllianceParam.valueOf(player, allianceId);
        param.setOperationType(operationType);
        param.setApplicationId(applicationId);

        Alliance alliance = getAlliance(allianceId);

        checkAllianceCondition(param, AllianceConst.APPLICATION_LEGAL);
        if (operationType == OperationType.Join_Alliance) {
            checkAllianceCondition(param, CheckType.Alliance_Full);
        }

        BaseAllianceApplication application = alliance.getApplicationMap().get(operationType).get(applicationId);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.APPLICATION_LEGAL);
            if (operationType == OperationType.Join_Alliance) {
                checkAllianceCondition(param, CheckType.Alliance_Full);
            }

            if (application.handler(agreed)) {
                saveAlliance();
            }
        }
    }

    @Override
    public void kickMember(Player player, long targetPlayerId) {
        // 不能踢自己
        if (player.getPlayerId() == targetPlayerId) {
            logger.warn("玩家[{}]踢人失败,目标对象不能为自己!", player.getAccountId());
            return;
        }
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        long allianceId = playerAllianceInfo.getAllianceId();
        Alliance alliance = getAlliance(allianceId);
        Player targetPlayer = SpringContext.getPlayerService().getPlayer(targetPlayerId);

        AllianceParam param = AllianceParam.valueOf(player, allianceId, targetPlayer);
        checkAllianceCondition(param, AllianceConst.KICK_MEMBER_ADMIN);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.KICK_MEMBER_ADMIN);
            alliance.forceLeave(targetPlayer);
        }
    }

    @Override
    public void promoteAdmin(Player player, long targetMemberId) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();
        Alliance alliance = getAlliance(allianceId);
        Player targetPlayer = SpringContext.getPlayerService().getPlayer(targetMemberId);
        AllianceParam param = AllianceParam.valueOf(player, allianceId, targetPlayer);

        checkAllianceCondition(param, AllianceConst.PROMOTE_ADMIN);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.PROMOTE_ADMIN);
            alliance.addAdmin(targetMemberId);
            saveAlliance();
        }
    }

    @Override
    public void dismissAlliance(Player player) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        AllianceParam param = AllianceParam.valueOf(player, allianceId);
        checkAllianceCondition(param, AllianceConst.DISMISS_ALLIANCE);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.DISMISS_ALLIANCE);
            alliance.dismiss();
            getServerAllianceInfo().removeAlliance(alliance);
        }

        saveAlliance();
    }

    @Override
    public void inviteJoinAlliance(Player player, String targetAccountId) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();

        AllianceParam param = new AllianceParam();
        param.setPlayer(player);
        param.setAllianceId(allianceId);

        checkAllianceCondition(param, AllianceConst.INVITE_JOIN_ALLIANCE);

        Player targetPlayer = SpringContext.getPlayerService().getPlayerByAccountId(targetAccountId);
        PlayerAllianceInfo allianceInfo = targetPlayer.getPlayerAllianceInfo();
        allianceInfo.addInvite(player.getPlayerId(), allianceId);
        SpringContext.getPlayerService().save(targetPlayer);
    }

    @Override
    public void handlerInvite(Player player, long inviteId, boolean agreed) {
        PlayerAllianceInfo allianceInfo = player.getPlayerAllianceInfo();
        if (!allianceInfo.containsInvite(inviteId)) {
            logger.warn("玩家[{}]处理邀请[{}]失败,不存在这条邀请!", player.getAccountId(), inviteId);
            return;
        }

        long allianceId = allianceInfo.getInvite(inviteId);
        Alliance alliance = getAlliance(allianceId);
        allianceInfo.removeInvite(inviteId);
        SpringContext.getPlayerService().save(player);
        if (!agreed) {
            return;
        }

        AllianceParam param =
            AllianceParam.valueOf(player, allianceId, SpringContext.getPlayerService().getPlayer(inviteId));
        checkAllianceCondition(param, AllianceConst.HANDLER_JOIN_ALLIANCE);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.HANDLER_JOIN_ALLIANCE);

            if (player.changeAllianceId(alliance.getAllianceId())) {
                alliance.addMember(player.getPlayerId());
            } else {
                // must be in other alliance
                logger.warn("玩家[{}]处理行会加入邀请失败,玩家已经是行会[{}]的成员!", player.getAccountId(), allianceInfo.getAllianceId());
                return;
            }
        }
        saveAlliance();
    }

    @Override
    public void playerAllianceInfoVo(Player player) {
        PacketUtil.send(player, SM_PlayerAllianceVo.valueOf(player.getPlayerAllianceInfo()));
    }

    @Override
    public void deliverChairman(Player player, long memberId) {
        if (player.getPlayerId() == memberId) {
            logger.warn("玩家[{}]转移会长失败,不能转交给自己!", player.getAccountId());
            return;
        }

        PlayerAllianceInfo allianceInfo = player.getPlayerAllianceInfo();
        long allianceId = allianceInfo.getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        AllianceParam param =
            AllianceParam.valueOf(player, allianceId, SpringContext.getPlayerService().getPlayer(memberId));
        checkAllianceCondition(param, AllianceConst.DELIVER_CHAIRMAN);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.DELIVER_CHAIRMAN);
            alliance.setChairmanId(memberId);
            alliance.addAdmin(memberId);
        }
        saveAlliance();
    }

    @Override
    public void demoteAdmin(Player player, long adminId) {
        if (player.getPlayerId() == adminId) {
            logger.warn("玩家[{}]降级管理员等级失败,不能自己操作自己!", player.getAccountId());
            return;
        }
        PlayerAllianceInfo allianceInfo = player.getPlayerAllianceInfo();
        long allianceId = allianceInfo.getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        AllianceParam param =
            AllianceParam.valueOf(player, allianceId, SpringContext.getPlayerService().getPlayer(adminId));
        checkAllianceCondition(param, AllianceConst.DEMOTE_ADMIN);

        synchronized (alliance.getLock()) {
            checkAllianceCondition(param, AllianceConst.DEMOTE_ADMIN);
            alliance.removeAdmin(adminId);
            saveAlliance();
        }
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
    public void sendAllianceInfo(Player player) {
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
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

    private void saveAlliance() {
        saveAllianceEnt(getAllianceEnt());
    }

    private void checkAllianceCondition(AllianceParam param, CheckType... args) {
        for (CheckType checkType : args) {
            checkType.check(param);
        }
    }

}
