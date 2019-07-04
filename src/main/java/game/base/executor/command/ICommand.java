package game.base.executor.command;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:05
 */

public interface ICommand {
    /**
     * 获取command的jey
     *
     * @return
     */
    Object getKey();

    /**
     * 获取任务名 例如command的类名
     *
     * @return
     */
    String getName();

    /**
     * 任务的执行逻辑
     */
    void active();

    /**
     * 获取线程编号
     *
     * @param poolSize
     * @return
     */
    int modIndex(int poolSize);

    /**
     * 是否被取消
     *
     * @return
     */
    boolean isCanceled();

    /**
     * 取消command
     */
    void cancel();

    /**
     * 刷新状态
     */
    void refreshState();
}
