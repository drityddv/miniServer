package middleware.resource.storage;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.base.map.IMap;
import game.scene.map.resource.MapResource;
import game.scene.map.resource.NoviceVillage;
import middleware.resource.middle.ResourceDefinition;

/**
 * 静态资源管理
 *
 * @author : ddv
 * @since : 2019/5/9 下午2:39
 */
@Component
public class StorageManager {

    private static final Logger logger = LoggerFactory.getLogger(StorageManager.class);

    // 静态类容器
    private Map<Class<?>, ResourceDefinition> definitionMap = new ConcurrentHashMap<>();

    // 静态资源存储容器
    private Map<Class<?>, Storage<?, ?>> storageMap = new ConcurrentHashMap<>();

    // csv读取的缓存流容器
    private Map<String, InputStream> caches = new ConcurrentHashMap<>();

    // 注册静态类目的地
    public void registerDefinition(Class<?> beanClass, ResourceDefinition definition) {
        if (!definitionMap.containsKey(beanClass)) {
            definitionMap.put(beanClass, definition);
            return;
        }

        logger.error("重复的加载行为,class[{}]", beanClass);
    }

    public void registerCsvCache(String location, InputStream inputStream) {
        if (!caches.containsKey(location)) {
            caches.put(location, inputStream);
            return;
        }
        logger.error("重复的加载行为,location[{}]", location);
    }

    // 初始化静态资源类 这一步要放到最后做
    public void initStorageMap() {
        IMap map = NoviceVillage.valueOf(1, 5, 5);
        Storage<Long, IMap> storage = new StorageLong<>();
        storage.storageAdd(1L, map);
        storageMap.put(MapResource.class, storage);
    }

    // get
    public Map<Class<?>, ResourceDefinition> getDefinitionMap() {
        return definitionMap;
    }

    public Map<Class<?>, Storage<?, ?>> getStorageMap() {
        return storageMap;
    }

    public Map<String, InputStream> getCaches() {
        return caches;
    }

    // 业务区
    // public <T, K> T getResource(Class<T> clazz, K key) {
    // Storage<?, ?> storage = storageMap.get(clazz);
    // }
}
