package game.base.log;

/**
 * @author : ddv
 * @since : 2020/3/5 3:46 PM
 */

public class Test {
    private static MyLogger myLogger = MyLogger.valueOf(Test.class);

    public static void main(String[] args) {
        myLogger.info("[{} {}]", 1, 2);
    }
}
