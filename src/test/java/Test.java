import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.SimpleUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    @org.junit.Test
    public void run() {
        String className = "game.base.map.base.AbstractGameMap";
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = clazz.getDeclaredFields();
        Stream.of(fields).filter(field -> SimpleUtil.isSimpleClazz(field.getType())).forEach(field -> System.out.println(field.getName() + " " + field.getType()));
    }

    public static void main(String[] args) throws FileNotFoundException {
        CSVParser parser = method();

        // Iterator<CSVRecord> iterator = parser.iterator();
        try {
            List<CSVRecord> records = parser.getRecords();
            records.stream().skip(2).forEach(record -> record.forEach(s -> System.out.println(s)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // iterator.forEachRemaining(strings -> System.out.println(strings));

        System.out.println();
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
