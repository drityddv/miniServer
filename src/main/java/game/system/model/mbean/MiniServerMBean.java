package game.system.model.mbean;

import javax.management.MXBean;

/**
 * @author : ddv
 * @since : 2019/8/6 10:38 PM
 */
@MXBean
public interface MiniServerMBean {
    /**
     * 在线玩家数目
     *
     * @return
     */
    int getOnlinePlayerCount();

    /**
     * 服务器当前的副本数量
     *
     * @return
     */
    int getInstanceCount();
}
