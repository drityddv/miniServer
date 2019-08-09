package game.publicsystem.alliance.service;

import game.publicsystem.alliance.model.Alliance;
import game.publicsystem.alliance.model.ServerAllianceInfo;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/8/3 11:09 PM
 */

public interface IAllianceService {

    /**
     * 创建公会
     *
     * @param player
     * @param allianceName
     */
    void createAlliance(Player player, String allianceName);

    /**
     * 管理员踢普通成员
     *
     * @param player
     * @param targetPlayerId
     */
    void kickMember(Player player, long targetPlayerId);

    /**
     * 获取工会
     *
     * @param allianceId
     * @return
     */
    Alliance getAlliance(long allianceId);

    /**
     * 获取服务器的所有工会信息
     *
     * @param player
     * @return
     */
    ServerAllianceInfo getAllianceInfo(Player player);

    /**
     * 投递加入工会申请
     *
     * @param player
     * @param allianceId
     */
    void pullJoinApplication(Player player, long allianceId);

    /**
     * 离开工会
     *
     * @param player
     */
    void leaveAlliance(Player player);

    /**
     * 处理公会相关申请
     *
     * @param player
     * @param operationTypeId
     * @param agreed
     */

    void handlerApplication(Player player, int operationTypeId, long applicationId, boolean agreed);

    /**
     * 向玩家发包公会信息
     *
     * @param player
     */
    void sendAllianceInfo(Player player);

    /**
     * 提拔成员为管理员
     *
     * @param player
     * @param targetMemberId
     */
    void promoteAdmin(Player player, long targetMemberId);

    /**
     * 解散行会
     *
     * @param player
     */
    void dismissAlliance(Player player);

    /**
     * 邀请人加入行会
     *
     * @param player
     * @param targetAccountId
     */
    void inviteJoinAlliance(Player player, String targetAccountId);

    /**
     * 玩家处理加入行会邀请
     *
     * @param player
     * @param inviteId
     * @param agreed
     */
    void handlerInvite(Player player, long inviteId, boolean agreed);

    /**
     * 玩家身上的行会信息vo
     *
     * @param player
     */
    void playerAllianceInfoVo(Player player);

    /**
     * 转移会长
     *
     * @param player
     * @param membrId
     */
    void deliverChairman(Player player, long membrId);
}
