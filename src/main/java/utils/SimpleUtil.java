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

    public static void insertField(Object object, List<Field> simpleFields, List<String> csvNames,
        List<String> values) {

        simpleFields.forEach(field -> {
            String name = field.getName();
            field.setAccessible(true);
            end:
            for (int i = 0; i < csvNames.size(); i++) {
                if (name.equals(csvNames.get(i).replaceAll(" ", ""))) {
                    String value = values.get(i);
                    if (value != null) {
                        value = value.replaceAll(" ", "");
                        try {
                            if (StringUtil.isNotEmpty(value)) {
                                field.set(object, JodaUtil.convertFromString(field.getType(), value));
                                break end;
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
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
