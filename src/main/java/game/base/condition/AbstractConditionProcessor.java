package game.base.condition;

import java.util.Map;

import game.role.player.model.Player;

/**
 * 条件满足检查器
 *
 * @author : ddv
 * @since : 2019/7/1 下午8:39
 */

public abstract class AbstractConditionProcessor {

    protected Map<Object, Integer> conditionParams;

    public AbstractConditionProcessor(Map<Object, Integer> conditionParams) {
        this.conditionParams = conditionParams;
    }

    /**
     * 检查逻辑
     *
     * @param player
     * @return
     */
    public abstract boolean doVerify(Player player);

    public Map<Object, Integer> getConditionParams() {
        return conditionParams;
    }

    public void setConditionParams(Map<Object, Integer> conditionParams) {
        this.conditionParams = conditionParams;
    }
}
