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
        // 修改id失败
        if (!success) {
            logger.warn("玩家[{}]创建公会失败,玩家已经是公会[{}]的成员了!", player.getAccountId(),
                playerPlayerAllianceInfo.getAllianceId());
            return;
        }

        AllianceEnt allianceEnt = getAllianceEnt();
        allianceEnt.getAllianceInfo().addAlliance(alliance);
        saveAlliance();
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void kickMember(Player player, long targetPlayerId) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        Alliance alliance = getAlliance(playerAllianceInfo.getAllianceId());

        if (playerAllianceInfo.getAllianceId() == AllianceConst.EMPTY_ALLIANCE_ID) {
            logger.warn("玩家[{}]踢人失败,玩家不属于任何公会", player.getAccountId());
            return;
        }

        if (alliance == null) {
            logger.warn("玩家[{}]踢人失败,公会不存在[{}]", player.getAccountId(), playerAllianceInfo.getAllianceId());
            return;
        }

        synchronized (alliance.getLock()) {
            if (!alliance.isMemberAdmin(player.getPlayerId())) {
                logger.warn("玩家[{}]踢人失败,玩家不是管理员", player.getAccountId());
                return;
            }

            if (alliance.isMemberAdmin(targetPlayerId)) {
                if (alliance.getChairmanId() != player.getPlayerId()) {
                    logger.warn("玩家[{}]踢人[{}]失败,目标也是管理员", player.getAccountId(), targetPlayerId);
                    return;
                }
            }

            if (!alliance.isMember(targetPlayerId)) {
                logger.warn("玩家[{}]踢人[{}]失败,目标不存在于公会之中", player.getAccountId(), targetPlayerId);
                return;
            }

            alliance.forceLeave(SpringContext.getPlayerService().getPlayer(targetPlayerId));
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
    public void pullJoinApplication(Player player, long allianceId) {
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
            synchronized (alliance.getLock()) {
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
    public void sendAllianceInfo(Player player) {
        PacketUtil.send(player, SM_ServerAllianceVo.valueOf(getServerAllianceInfo()));
    }

    @Override
    public void promoteAdmin(Player player, long targetMemberId) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        if (alliance == null) {
            logger.warn("玩家[{}]提拔管理员失败,玩家无行会!", player.getAccountId());
            return;
        }

        if (alliance.getChairmanId() != player.getPlayerId()) {
            logger.warn("玩家[{}]提拔管理员失败,不是会长!", player.getAccountId());
            return;
        }

        synchronized (alliance.getLock()) {
            if (!alliance.isMember(targetMemberId)) {
                logger.warn("玩家[{}]提拔管理员失败,成员[{}]不是会员!", player.getAccountId(), targetMemberId);
                return;
            }

            if (alliance.isMemberAdmin(targetMemberId)) {
                logger.warn("玩家[{}]提拔管理员失败,成员[{}]已经是管理员!", player.getAccountId(), targetMemberId);
                return;
            }

            alliance.addAdmin(targetMemberId);
            saveAlliance();
        }
    }

    @Override
    public void dismissAlliance(Player player) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        if (alliance == null) {
            logger.warn("玩家[{}]解散行会失败,玩家无行会!", player.getAccountId());
            return;
        }

        if (alliance.getChairmanId() != player.getPlayerId()) {
            logger.warn("玩家[{}]解散行会失败,不是会长!", player.getAccountId());
            return;
        }

        synchronized (alliance.getLock()) {
            alliance.dismiss();
        }

        getServerAllianceInfo().removeAlliance(alliance);
        saveAlliance();
    }

    @Override
    public void inviteJoinAlliance(Player player, String targetAccountId) {
        long allianceId = player.getPlayerAllianceInfo().getAllianceId();
        Alliance alliance = getAlliance(allianceId);

        if (alliance == null) {
            logger.warn("玩家[{}]邀请失败,玩家无行会!", player.getAccountId());
            return;
        }

        Player targetPlayer = SpringContext.getPlayerService().getPlayerByAccountId(targetAccountId);
        PlayerAllianceInfo allianceInfo = targetPlayer.getPlayerAllianceInfo();
        if (allianceInfo.isInAlliance()) {
            logger.warn("玩家[{}]邀请失败,邀请对象[{}]已经存在行会[{}]中!", player.getAccountId(), targetPlayer.getAccountId(),
                allianceInfo.getAllianceId());
            return;
        }

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

        if (alliance == null) {
            logger.warn("玩家[{}]处理行会加入邀请失败,对应行会[{}]已经解散!", player.getAccountId(), allianceId);
            return;
        }

        if (agreed) {
            synchronized (alliance.getLock()) {
                if (!alliance.isMember(inviteId)) {
                    logger.warn("玩家[{}]处理行会[{}]加入邀请失败,邀请者身份失效!", player.getAccountId(), inviteId);
                    return;
                }

                if (alliance.isMember(player.getPlayerId())) {
                    logger.warn("玩家[{}]处理行会[{}]加入邀请失败,玩家已经是该行会的成员了!", player.getAccountId(), allianceId);
                    return;
                }
                alliance.addMember(player.getPlayerId());
            }
            saveAlliance();
        }

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
        Alliance alliance = getAlliance(allianceInfo.getAllianceId());

        if (alliance == null || alliance.getChairmanId() != player.getPlayerId()) {
            logger.warn("玩家[{}]转移会长失败,玩家无公会或者玩家不是会长!", player.getAccountId());
            return;
        }

        synchronized (alliance.getLock()) {
            if (!alliance.isMember(memberId)) {
                logger.warn("玩家[{}]转移会长失败,移交对象[{}]不是行会成员!", player.getAccountId(), memberId);
                return;
            }

            alliance.setChairmanId(memberId);
            alliance.addAdmin(memberId);
        }
        saveAlliance();
    }

    @Override
    public void leaveAlliance(Player player) {
        PlayerAllianceInfo playerAllianceInfo = player.getPlayerAllianceInfo();
        long allianceId = playerAllianceInfo.getAllianceId();

        // 提交离开申请 此时可能已经不在了
        if (!playerAllianceInfo.isInAlliance()) {
            logger.warn("玩家[{}]主动离开行会失败,玩家已经不在行会了!", player.getAccountId());
            return;
        }

        Alliance alliance = getAlliance(allianceId);

        if (alliance == null || alliance.isDismiss()) {
            logger.warn("玩家[{}]主动离开行会失败,行会已经失效,无需手动离开!", player.getAccountId());
            return;
        }

        // 会长暂时不允许离开
        if (alliance.getChairmanId() == player.getPlayerId()) {
            logger.warn("玩家[{}]主动离开行会失败,玩家是会长!", player.getAccountId());
            return;
        }

        synchronized (alliance.getLock()) {
            if (alliance.isDismiss()) {
                logger.warn("玩家[{}]主动离开行会失败,行会已经解散,无需手动离开!", player.getAccountId());
                return;
            }
            alliance.forceLeave(player);
        }

        saveAlliance();
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

        synchronized (alliance.getLock()) {
            if (!alliance.isMemberAdmin(player.getPlayerId())) {
                logger.warn("玩家[{}]处理申请失败,玩家不是管理员", player.getPlayerId());
                return;
            }

            BaseAllianceApplication application = alliance.getApplicationMap().get(operationType).get(applicationId);
            if (application == null || application.isExpired()) {
                logger.warn("玩家[{}]处理申请失败,申请不存在或者过期", player.getPlayerId());
                return;
            }

            if (application.handler(agreed)) {
                saveAlliance();
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

    private void saveAlliance() {
        saveAllianceEnt(getAllianceEnt());
    }

}
