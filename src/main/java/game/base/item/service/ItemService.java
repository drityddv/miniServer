package game.base.item.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.item.base.model.AbstractItem;
import game.base.item.model.ItemDropParam;
import game.base.item.resource.ItemDropResource;
import game.base.item.resource.ItemResource;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import utils.MathUtil;
import utils.snow.IdUtil;

/**
 * 道具创建 掉落service
 *
 * @author : ddv
 * @since : 2019/7/10 下午7:54
 */
@Component
public class ItemService implements IItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemManager itemManager;

    @Override
    public AbstractItem createItem(long configId, int num) {
        ItemResource itemResource = itemManager.getItemResource(configId);
        if (itemResource == null) {
            logger.warn("配置表id:[{}]的资源文件不存在", configId);
            RequestException.throwException(I18N.RESOURCE_NOT_EXIST);
        }
        AbstractItem abstractItem = itemResource.getItemType().create();
        abstractItem.setObjectId(IdUtil.getLongId());
        abstractItem.init(itemResource);
        abstractItem.setNum(num);
        return abstractItem;
    }

    @Override
    public List<AbstractItem> createItems(long configId, int num) {
        List<AbstractItem> items = new ArrayList<>();
        while (num > 0) {
            items.add(createItem(configId, 1));
            num--;
        }
        return items;
    }

    @Override
    public ItemResource getItemResource(Long configId) {
        return itemManager.getItemResource(configId);
    }

    @Override
    public List<AbstractItem> createItemsByDropConfig(long dropConfigId) {
        List<AbstractItem> abstractItems = new ArrayList<>();
        ItemDropResource itemDropResource = itemManager.getItemDropResource(dropConfigId);

        if (itemDropResource == null) {
            logger.warn("创建道具失败,道具掉落表[{}] 资源文件缺失", dropConfigId);
            return abstractItems;
        }

        List<ItemDropParam> dropParamList = itemDropResource.getDropParamList();
        dropParamList.forEach(itemDropParam -> {
            if (MathUtil.gamble(itemDropParam.getRadio())) {
                abstractItems.addAll(createItems(itemDropParam.getItemId(), itemDropParam.getNum()));
            }
        });

        return abstractItems;
    }
}
