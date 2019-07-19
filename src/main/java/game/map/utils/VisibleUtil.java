package game.map.utils;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.map.visible.AbstractVisibleMapObject;

/**
 * @author : ddv
 * @since : 2019/7/3 下午3:24
 */

public class VisibleUtil {

    public static boolean doMove(AbstractVisibleMapObject object, int[][] blockData) {
        int targetX = object.getTargetGrid().getX();
        int targetY = object.getTargetGrid().getY();

        try {
            if (object.getFighterAccount().getCreatureUnit().isCanMove()) {
                int blockPoint = blockData[targetX][targetY];
                if (blockPoint == 1) {
                    object.doMove();
                    return true;
                }
            }
        } catch (NullPointerException e) {
            RequestException.throwException(I18N.TARGET_POSITION_ERROR);
        }
        return false;
    }
}
