package utils;

/**
 * @author : ddv
 * @since : 2019/12/17 3:01 PM
 */

public class OsUtil {

    public static String getSignalName() {
        return System.getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "USR2";
    }
}
