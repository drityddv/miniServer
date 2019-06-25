package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author : ddv
 * @since : 2019/6/23 下午3:48
 */

public class ResourceUtil {

    // 加载时从csv文件读取字段
    public static List loadObjectFromCsv(Class<?> tClass, CSVParser parser) {
        List targetList = new ArrayList<>();
        List<String> fieldNames;
        List<Field> simpleFields;
        try {
            List<CSVRecord> records = parser.getRecords();
            fieldNames = SimpleUtil.toListFromIterator(records.get(0).iterator());
            simpleFields = new ArrayList<>();

            for (String fieldName : fieldNames) {
                Field field = tClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                simpleFields.add(field);
            }

            List<CSVRecord> fieldValues = records.stream().skip(1).collect(Collectors.toList());

            for (CSVRecord record : fieldValues) {
                Object instance = tClass.newInstance();
                List<String> values = SimpleUtil.toListFromIterator(record.iterator());
                SimpleUtil.insertField(instance, simpleFields, fieldNames, values);
                targetList.add(instance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return targetList;
    }
}
