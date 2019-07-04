package game.base.executor.scene;

import game.base.executor.command.AbstractCommand;
import game.base.executor.command.ICommand;

/**
 * @author : ddv
 * @since : 2019/7/2 上午11:39
 */

public interface ISceneExecutorService {
    /**
     * 增加命令
     *
     * @param command
     */
    void submit(ICommand command);

    /**
     * 延时执行
     *
     * @param command
     * @param delay
     */
    void schedule(AbstractCommand command, long delay);

    /**
     * 初始化
     */
    void init();

    /**
     * 关闭支持
     */
    void shutdown();
}
