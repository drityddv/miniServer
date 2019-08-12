package game.publicsystem.alliance.constant;

/**
 * @author : ddv
 * @since : 2019/8/3 11:47 PM
 */

public interface AllianceConst {

    // 最大人数限制
    int MAX_MEMBER_SIZE = 100;

    long EMPTY_ALLIANCE_ID = 0;

    long SERVER_ID = 1;
    /**
     * 行会合法性校验
     */
    CheckType[] ALLIANCE_LEGAL = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS};
    /**
     * 邀请他人加入行会
     */
    CheckType[] INVITE_JOIN_ALLIANCE = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Member, CheckType.Alliance_Full};
    /**
     * 玩家离开行会
     */
    CheckType[] LEAVE_ALLIANCE = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Member, CheckType.Is_Operator_Not_Chairman};
    /**
     * 申请表检查
     */
    CheckType[] APPLICATION_LEGAL = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Application_Legal, CheckType.Is_Operator_Admin};
    /**
     * 管理员踢人验证
     */
    CheckType[] KICK_MEMBER_ADMIN = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Admin, CheckType.Is_Target_Member, CheckType.Is_Target_Not_Admin};
    /**
     * 提拔管理员验证
     */
    CheckType[] PROMOTE_ADMIN = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Chairman, CheckType.Is_Target_Member, CheckType.Is_Target_Not_Admin};
    /**
     * 解散行会
     */
    CheckType[] DISMISS_ALLIANCE =
        new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS, CheckType.Is_Operator_Chairman};
    /**
     * 接受邀请加入行会
     */
    CheckType[] HANDLER_JOIN_ALLIANCE = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Not_Member, CheckType.Is_Target_Member, CheckType.Alliance_Full};
    /**
     * 移交会长
     */
    CheckType[] DELIVER_CHAIRMAN = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Chairman, CheckType.Is_Target_Member};
    /**
     * 降级管理员
     */
    CheckType[] DEMOTE_ADMIN = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Chairman, CheckType.Is_Target_Admin};
}
