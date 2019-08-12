package game.publicsystem.alliance.constant;

/**
 * @author : ddv
 * @since : 2019/8/3 11:47 PM
 */

public interface AllianceConst {

    long EMPTY_ALLIANCE_ID = 0;

    long SERVER_ID = 1;
    // 行会合法性校验
    CheckType[] ALLIANCE_LEGAL = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS};
    // 邀请他人加入行会
    CheckType[] INVITE_JOIN_ALLIANCE =
        new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS, CheckType.Is_Operator_Member};
    // 玩家离开行会
    CheckType[] LEAVE_ALLIANCE = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Operator_Member, CheckType.Is_Operator_Chairman};
    // 申请表检查
    CheckType[] APPLICATION_LEGAL = new CheckType[] {CheckType.Alliance_Empty, CheckType.Alliance_DISMISS,
        CheckType.Is_Application_Legal, CheckType.Is_Operator_Admin};

}
