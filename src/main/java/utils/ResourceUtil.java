package utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.attribute.LockAttribute;

/**
 * @author : ddv
 * @since : 2019/6/23 下午3:48
 */

public class ResourceUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

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
                Field field = tClass.getDeclaredField(fieldName.replaceAll(" ", ""));
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
            logger.error("加载资源文件[{}]出错", tClass.getSimpleName());
            e.printStackTrace();
        }

        return targetList;
    }

    // 实际返回的是LockAttribute
    public static List<Attribute> initAttrs(String attributeString) {
        List<Attribute> attributeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(attributeString)) {
            attributeList = new ArrayList<>();
            List<Attribute> temp = new ArrayList<>();
            String[] attrs = attributeString.split(",");
            for (String attr : attrs) {
                String[] params = attr.split(":");
                temp.add(Attribute.valueOf(AttributeType.getByName(params[0]),
                    JodaUtil.convertFromString(Integer.class, params[1])));
            }
            for (Attribute attribute : temp) {
                attributeList.add(LockAttribute.wrapper(attribute));
            }
        }
        return attributeList;
    }

    public static Map<String, Object> getYmlRoot(String fileName) {
        InputStream in = ResourceUtil.class.getClassLoader().getResourceAsStream("server-dev.yml");
        Map<String, Object> root = null;
        Yaml yaml = new Yaml();
        try {
            root = yaml.loadAs(in, Map.class);
        } catch (Exception e) {
            logger.error("解析[{}]文件错误,文件缺失", fileName);
            e.printStackTrace();
        }
        return root;
    }

    public static Map<String, Object> getNode(String key, Map<String, Object> node) {
        return (Map<String, Object>)node.get(key);
    }

}
