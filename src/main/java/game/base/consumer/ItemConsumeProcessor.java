package game.base.consumer;

import java.util.Map;

import game.common.exception.RequestException;
import game.role.player.model.Player;
import game.user.item.base.model.AbstractItem;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/28 下午2:36
 */

public class ItemConsumeProcessor extends AbstractConsumeProcessor {

    public ItemConsumeProcessor(Map<Long, Integer> consumeParams) {
        super(consumeParams);
    }

    @Override
    public void doConsume(Player player) {
        try {
            consumeParams.forEach((itemConfigId, num) -> {
                AbstractItem item = SpringContext.getCommonService().createItem(itemConfigId, num);
                SpringContext.getPackService().reduceItem(player, item, num);
            });
        } catch (RequestException e) {
            throw e;
        }
    }
}
