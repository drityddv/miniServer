package game.world.instance.base.constant;

/**
 * @author : ddv
 * @since : 2019/8/1 9:33 PM
 */

public interface InstanceConst {

    /**
     * 副本结算后延迟关闭时间 毫秒
     */
    long DELAY_CLOSE_INSTANCE = 10000;
    /**
     * 副本开始时间与现在相差分钟数大于 摧毁副本
     */
    int INSTANCE_DESTROY_MINUTES = 5;

    /**
     * 副本最大人数
     */
    int MAX_PLAYER_SIZE = 20;
}
