package game.common.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import middleware.sehedule.QuartzService;
import utils.IdUtil;

/**
 * 公共服务
 *
 * @author : ddv
 * @since : 2019/7/8 下午9:02
 */
@Component
public class CommonService implements ICommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private CommonManager commonManager;

    @Autowired
    private QuartzService quartzService;

    @Override
    public AbstractItem createItem(long configId, int num) {
        ItemResource itemResource = commonManager.getItemResource(configId);
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
    public void initPublicTask() {

    }
}
