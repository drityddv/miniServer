package game.user.equip.base.consumer;

import java.util.Map;

import game.common.exception.RequestException;
import game.user.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/6/28 下午3:27
 */

public abstract class AbstractConsumeProcessor {

    protected Map<Long, Integer> consumeParams;

    public AbstractConsumeProcessor(Map<Long, Integer> consumeParams) {
        this.consumeParams = consumeParams;
    }

    /**
     * 消耗逻辑 消耗失败不会返还已经消耗的道具
     *
     * @param player
     * @throws Exception
     */
    public abstract void doConsume(Player player) throws RequestException;

    public Map<Long, Integer> getConsumeParams() {
        return consumeParams;
    }

    public void setConsumeParams(Map<Long, Integer> consumeParams) {
        this.consumeParams = consumeParams;
    }
}
