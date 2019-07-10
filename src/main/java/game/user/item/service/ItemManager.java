package game.user.item.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import game.user.item.resource.ItemResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/10 下午7:57
 */
@Component
public class ItemManager {

    @Static
    private Map<Long, ItemResource> itemResources;

    public ItemResource getItemResource(long configId) {
        return itemResources.get(configId);
    }
}
