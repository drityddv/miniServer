package utils;

import java.util.Random;

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

    /**
     * 生成max到min范围的浮点数
     */
    public static double getRandomDouble(double min, double max) {
        return min + (new Random().nextDouble() * (max - min));
    }

    /**
     * 给定一个0~1范围内double数字[开区间] 根据数字大小进行一次随机命中 例如 num = 0.41 生成0~1的随机数 大于0.41则失败
     *
     * @param num
     * @return
     */
    public static boolean gamble(double num) {
        return getRandomDouble(0, 1) <= num;
    }

    public static long getLongRandom(long left, long right) {
        return left + (((long)(new Random().nextDouble() * (right - left + 1))));
    }

}
