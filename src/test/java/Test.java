import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import game.base.executor.NameThreadFactory;
import middleware.anno.MapResource;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    public static void main(String[] args) {
//        int processors = Runtime.getRuntime().availableProcessors();
        int processors = 10;

        ThreadPoolExecutor[] accountExecutor = new ThreadPoolExecutor[processors];

        for (int i = 0; i < processors; i++) {
            ThreadPoolExecutor thread =
                new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new NameThreadFactory("accountThread", i), new ThreadPoolExecutor.AbortPolicy());
            accountExecutor[i] = thread;
        }

        for (int i = 0; i < 100; i++) {
            final int index = i;
            accountExecutor[i % processors].submit(() -> {
                System.out.println(Thread.currentThread().getName() + " " + index);
            });
        }
    }
}
