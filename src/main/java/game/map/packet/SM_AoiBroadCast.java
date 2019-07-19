package game.map.packet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.map.model.AoiBroadCastVo;
import game.map.visible.AbstractVisibleMapObject;

/**
 * @author : ddv
 * @since : 2019/7/19 10:37 AM
 */

public class SM_AoiBroadCast {
    private static final Logger logger = LoggerFactory.getLogger(SM_AoiBroadCast.class);

    private List<AoiBroadCastVo> mapInfoList = new ArrayList<>();

    public static SM_AoiBroadCast valueOf(List<AbstractVisibleMapObject> mapInfoList) {
        SM_AoiBroadCast sm = new SM_AoiBroadCast();
        for (AbstractVisibleMapObject mapInfo : mapInfoList) {
            sm.mapInfoList.add(AoiBroadCastVo.valueOf(mapInfo));
        }
        return sm;
    }

    @Action
    private void action() {
        for (AoiBroadCastVo vo : mapInfoList) {
            logger.info("单位[{}] [{}] HP[{}] MP[{}] 坐标[{} {}]", vo.getUnitName(), vo.getId(), vo.getHp(), vo.getMp(),
                vo.getX(), vo.getY());
        }
    }

}
