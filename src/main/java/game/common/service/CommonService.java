package game.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.common.I18N;
import game.common.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import utils.IdUtil;

/**
 * @author : ddv
 * @since : 2019/7/8 下午9:02
 */
@Component
public class CommonService implements ICommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private CommonManager commonManager;

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
}
