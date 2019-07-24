package game.base.buff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.resource.BuffResource;

/**
 * @author : ddv
 * @since : 2019/7/24 10:42 AM
 */
@Component
public class BuffService {

    @Autowired
    private BuffManager buffManager;

    /**
     * 创建buff 自行初始化
     *
     * @param configId
     * @return
     */
    public BaseCreatureBuff createBuffByConfigId(long configId) {
        return buffManager.getBuffResourceById(configId).createBuff();
    }

    public BuffResource getBuffResource(long configId) {
        return buffManager.getBuffResourceById(configId);
    }

    public boolean isAllowAddBuff() {
        return false;
    }

}
