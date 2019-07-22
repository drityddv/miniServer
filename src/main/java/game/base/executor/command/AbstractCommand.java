package game.base.executor.command;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:03
 */

public abstract class AbstractCommand implements ICommand {
    /**
     * 是否被取消
     */
    private boolean isCanceled = false;

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public void refreshState() {
        isCanceled = false;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

}
