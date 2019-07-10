package game.map.utils;

import java.util.List;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.map.visible.AbstractVisibleMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/3 下午3:24
 */

public class VisibleUtil {

    // 移动
    public static void doMove(List<? extends AbstractVisibleMapInfo> visibleObjects) {
        visibleObjects.forEach(AbstractVisibleMapInfo::doMove);
    }

    public static void doMove(AbstractVisibleMapInfo info, int[][] blockData) {
        int targetX = info.getTargetX();
        int targetY = info.getTargetY();

        try {
            int blockPoint = blockData[targetX][targetY];
            if (blockPoint != 1) {
                info.doMove();
            }
        } catch (NullPointerException e) {
            RequestException.throwException(I18N.TARGET_POSITION_ERROR);
        }
    }
}
