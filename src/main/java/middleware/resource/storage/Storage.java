package middleware.resource.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/5/10 下午8:13
 */

public class Storage<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private Map<K, V> data = new HashMap<>();

    private Map<K, List<V>> dataList = new HashMap<>();

    public void addIntoStorageMap(K k, V v) {
        data.put(k, v);
    }

    public V getFromStorageMap(K k) {
        return data.get(k);
    }

    public Map<K, V> getStorageMap() {
        return data;
    }

    public Map<K, List<V>> getStorageListMap() {
        return dataList;
    }

    public List<V> getFromStorageList(K k) {
        return dataList.get(k);
    }

    public void addIntoStorageList(K k, V v) {
        List<V> list = getFromStorageList(k);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(v);
    }
}
