package game.base.consumer;

import java.util.Iterator;
import java.util.Map;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.role.player.model.Player;
import game.user.item.base.model.AbstractItem;
import game.user.pack.service.IPackService;
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

        Iterator<Map.Entry<Long, Integer>> iterator = consumeParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Integer> next = iterator.next();
            long itemConfigId = next.getKey();
            int num = next.getValue();
            IPackService packService = SpringContext.getPackService();
            AbstractItem item = SpringContext.getItemService().createItem(itemConfigId, num);
            boolean enoughSize = packService.isEnoughSize(player, item);
            if (!enoughSize) {
                RequestException.throwException(I18N.ITEM_NUM_NOT_ENOUGH);
            }
        }

        consumeParams.forEach((itemConfigId, num) -> {
            IPackService packService = SpringContext.getPackService();
            AbstractItem item = SpringContext.getItemService().createItem(itemConfigId, num);
            packService.reduceItem(player, item);
        });
    }
}
