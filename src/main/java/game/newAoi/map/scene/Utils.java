package game.newAoi.map.scene;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author : ddv
 * @since : 2020/1/15 11:57 AM
 */

public class Utils {

    public static void logArray2(int[][] mapData) {
        for (int[] mapDatum : mapData) {
            logArray(mapDatum);
        }
    }

    public static void logArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
        }
        System.out.println();
    }

    public static <T> T getRandomElementFromList(List<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }

        int index = RandomUtils.nextInt(0, collection.size());
        T element = collection.get(index);
        collection.remove(element);
        return element;
    }

}
