package game.publicsystem.alliance.packet;

/**
 * @author : ddv
 * @since : 2019/8/5 8:32 PM
 */

public class CM_LeaveApplication {
    // 是否强退(不走审批流程)
    private int force;

    public int getForce() {
        return force;
    }
}
