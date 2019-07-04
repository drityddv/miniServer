package game.miniMap.utils;

import java.util.List;

import game.miniMap.visible.AbstractVisibleMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午3:24
 */

public class VisibleUtil {

    // 移动
    public static void doMove(List<? extends AbstractVisibleMapInfo> visibleObjects) {
        visibleObjects.forEach(AbstractVisibleMapInfo::doMove);
    }
}
