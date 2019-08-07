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
     * 加载服务器工会数据
     */
    void init();

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
     * @return
     */
    ServerAllianceInfo getAllianceInfo(Player player);

    /**
     * 投递加入工会申请
     *
     * @param player
     * @param allianceId
     */
    void joinApplication(Player player, long allianceId);

    /**
     * 离开工会
     *
     * @param player
     * @param force
     *            是否强退
     */
    void leaveApplication(Player player, boolean force);

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
}
