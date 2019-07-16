package game.base.fight.trigger.result.impl;

import game.base.fight.trigger.result.IExecuteResult;

/**
 * @author : ddv
 * @since : 2019/7/15 9:34 PM
 */

public abstract class BaseExecuteResult<T extends BaseExecuteResult> implements IExecuteResult {

    @Override
    public final void merge(IExecuteResult result) {
        if (!this.getClass().equals(result.getClass())) {
            return;
        }
        if (this == result) {
            return;
        }
        doMerge((T)result);
    }

    /**
     * 合并
     *
     * @param result
     */
    protected abstract void doMerge(T result);
}
