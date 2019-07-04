package game.base.executor.command;

import java.util.concurrent.ScheduledFuture;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:03
 */

public abstract class AbstractCommand implements ICommand {
    /**
     * 是否被取消
     */
    private boolean isCanceled = false;

    /**
     * 定时任务
     */
    private ScheduledFuture future;

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void cancel() {
        if (future != null) {
            future.cancel(true);
        }
        isCanceled = true;
    }

    @Override
    public void refreshState() {
        isCanceled = false;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    public ScheduledFuture getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
}
