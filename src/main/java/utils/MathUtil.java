package utils;

/**
 * @author : ddv
 * @since : 2019/7/23 10:22 PM
 */

public class MathUtil {
    /**
     * 求最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    public static long getGcd(long a, long b) {
        long max, min;
        max = (a > b) ? a : b;
        min = (a < b) ? a : b;

        if (max % min != 0) {
            return getGcd(min, max % min);
        }
        return min;

    }
}
