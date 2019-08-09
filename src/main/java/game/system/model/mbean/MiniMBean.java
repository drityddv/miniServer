package game.system.model.mbean;

import javax.management.MXBean;

import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.world.instance.singleIntance.model.SingleInstanceMapInfo;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/8/6 10:30 PM
 */
@MXBean
public class MiniMBean implements MiniServerMBean {

    @Override
    public int getOnlinePlayerCount() {
        return SpringContext.getSessionManager().getSize();
    }

    @Override
    public int getInstanceCount() {
        long count = 0;
        // 暂时只有10 和 11 是副本 10是单人副本 11是多人副本
        AbstractMapHandler abstractMapHandler = AbstractMapHandler.getAbstractMapHandler(10);
        AbstractMovableScene mapScene = abstractMapHandler.getMapScene(10, 10);
        SingleInstanceMapInfo baseMapInfo = (SingleInstanceMapInfo)mapScene.getBaseMapInfo();
        return baseMapInfo.getSize();

    }
}
