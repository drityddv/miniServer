import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    @org.junit.Test
    public void run() {
        String className = "game.scene.map.resource.NoviceVillage";
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            try {
                fields[1].set(instance, new Long(1));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.println(instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/csv/MapResource.csv");
        InputStream inputStream = new FileInputStream(file);
        ArrayList list = new ArrayList();
        try {
            CSVParser parser = CSVParser.parse(inputStream, Charset.forName("utf-8"), CSVFormat.DEFAULT);
            for (CSVRecord record : parser) {
                Iterator<String> iterator = record.iterator();
                iterator.forEachRemaining(s -> System.out.println(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
