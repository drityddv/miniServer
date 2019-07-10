package game.system;

/**
 * @author : ddv
 * @since : 2019/7/10 下午8:46
 */

public interface ISystemService {

    /**
     * 关服
     */
    void serverClose();

    /**
     * 初始化监控,系统钩子等...
     */
    void init();
}
