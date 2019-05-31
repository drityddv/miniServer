import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import middleware.anno.MapResource;
import utils.ClassUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    @org.junit.Test
    public void run() {
        try {
            Class clazz = Class.forName("game.base.map.IMap");
            Annotation annotation = clazz.getAnnotation(MapResource.class);
            System.out.println(annotation);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName("game.user.login.packet.CM_UserLogin");
            Object object = aClass.newInstance();

			ClassUtil.insertDefaultFields(object, Arrays.asList("account—_1","112233"));

			System.out.println(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CSVParser method() throws FileNotFoundException {
        File file = new File("src/main/resources/csv/MapResource.csv");
        InputStream inputStream = new FileInputStream(file);
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(inputStream, Charset.forName("utf-8"), CSVFormat.DEFAULT);
            // for (CSVRecord record : parser) {
            // Iterator<String> iterator = record.iterator();
            // iterator.forEachRemaining(s -> System.out.println(s));
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser;
    }
}
