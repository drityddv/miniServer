package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import game.role.player.model.Player;
import net.model.USession;
import spring.SpringContext;

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

    public static CSVParser getParserFromStream(InputStream inputStream) {
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(inputStream, Charset.forName("utf-8"), CSVFormat.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parser;
    }

    // 用的时候再加 [int,long,string]
    public static boolean isSimpleClazz(Class<?> clazz) {
        if (clazz == int.class || clazz == Integer.class) {
            return true;
        }

        if (clazz == long.class || clazz == Long.class) {
            return true;
        }

        if (clazz == String.class) {
            return true;
        }

        return false;
    }

    public static boolean isSimpleClazzByName(String clazzName) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            return false;
        }

        return isSimpleClazz(aClass);

    }

    public static void insertField(Object object, List<Field> simpleFields, List<String> csvNames,
        List<String> values) {

        simpleFields.forEach(field -> {
            String name = field.getName();
            field.setAccessible(true);

            for (int i = 0; i < csvNames.size(); i++) {
                if (name.equals(csvNames.get(i))) {
                    try {
                        if (StringUtil.isNotEmpty(values.get(i))) {
                            field.set(object, JodaUtil.convertFromString(field.getType(), values.get(i)));
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    public static Player getPlayerFromSession(USession session) {
        String accountId = getAccountIdFromSession(session);
        return SpringContext.getPlayerService().getPlayerByAccountId(accountId);
    }

    public static List<String> toListFromIterator(Iterator<String> iterable) {
        List<String> list = new ArrayList<>();
        iterable.forEachRemaining(list::add);
        return list;
    }
}
