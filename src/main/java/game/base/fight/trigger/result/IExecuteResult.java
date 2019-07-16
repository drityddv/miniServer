package game.base.fight.trigger.result;

/**
 * 执行结果
 *
 * @author : ddv
 * @since : 2019/7/15 9:32 PM
 */

public interface IExecuteResult {
    /**
     * 结果合并
     *
     * @param result
     */
    void merge(IExecuteResult result);
}
