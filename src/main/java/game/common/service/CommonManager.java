package game.common.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import game.base.item.resource.ItemResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/8 下午9:03
 */
@Component
public class CommonManager {

    @Static
    private Map<Long, ItemResource> itemResources;

    public ItemResource getItemResource(long configId) {
        return itemResources.get(configId);
    }
}
