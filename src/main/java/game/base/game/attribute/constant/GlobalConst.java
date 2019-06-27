package game.base.game.attribute.constant;

/**
 * @author : ddv
 * @since : 2019/6/27 上午11:31
 */

public interface GlobalConst {

    int RATIO_MAX_VALUE = 100;

    /**
     * 获取比例
     *
     * @param value
     * @return
     */
    static double getRatio(double value) {
        return value / RATIO_MAX_VALUE;
    }

}
