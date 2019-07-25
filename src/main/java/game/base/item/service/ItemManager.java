package game.base.item.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import game.base.item.resource.ItemDropResource;
import game.base.item.resource.ItemResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/10 下午7:57
 */
@Component
public class ItemManager {

    @Static
    private Map<Long, ItemResource> itemResources;

    @Static
    private Map<Long, ItemDropResource> itemDropResourceMap;

    public ItemResource getItemResource(long configId) {
        return itemResources.get(configId);
    }

    public ItemDropResource getItemDropResource(long configId) {
        return itemDropResourceMap.get(configId);
    }
}
