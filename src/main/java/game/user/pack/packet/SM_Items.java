package game.user.pack.packet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.base.item.base.model.AbstractItem;

/**
 * @author : ddv
 * @since : 2019/7/30 4:36 PM
 */

public class SM_Items {
    private static final Logger logger = LoggerFactory.getLogger(SM_Items.class);
    private List<Long> itemIdList;

    public static SM_Items valueOf(List<AbstractItem> items) {
        SM_Items sm = new SM_Items();
        sm.itemIdList = new ArrayList<>();
        items.forEach(abstractItem -> {
            sm.itemIdList.add(abstractItem.getConfigId());
        });
        return sm;
    }

    @Action
    private void action() {
        logger.info("发奖列表[{}]", itemIdList);
    }
}
