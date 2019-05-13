package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.model.USession;

/**
 * 无分类的工具包
 *
 * @author : ddv
 * @since : 2019/5/8 上午11:18
 */

public class SimpleUtil {

    public static String getAccountIdFromSession(USession session) {
        return (String)session.getAttributes().get("accountId");
    }

    public static InputStream getInputStreamFromFile(String fileLocation) {
        fileLocation = fileLocation + ".csv";
        InputStream inputStream = null;
        File file = new File(fileLocation);
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}
