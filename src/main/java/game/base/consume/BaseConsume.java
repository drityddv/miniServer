package game.base.consume;

import game.base.message.exception.RequestException;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/11 8:48 PM
 */

public abstract class BaseConsume implements IConsume {
    /**
     * 解析参数
     *
     * @param value
     */
    public abstract void doParse(String value);

    /**
     * 检查
     *
     * @param player
     * @param result
     */
    public abstract void doVerify(Player player, VerifyResult result);

    /**
     * 消耗
     *
     * @param player
     */
    public abstract void doConsume(Player player);

    /**
     * 合并
     *
     * @param consume
     * @return
     */
    public abstract boolean merge(BaseConsume consume);

    @Override
    public void verifyThrow(Player player) throws RequestException {
        VerifyResult result = new VerifyResult();
        doVerify(player, result);
        if (!result.isSuccess()) {
            RequestException.throwException(result.getCode());
        }
    }

    @Override
    public boolean verify(Player player) {
        VerifyResult result = new VerifyResult();
        doVerify(player, result);
        return result.isSuccess();
    }

    @Override
    public void consume(Player player) {
        doConsume(player);
    }
}
