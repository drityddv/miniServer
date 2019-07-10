package game.role.equip.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.role.equip.entity.EquipStorageEnt;
import game.role.equip.recource.EquipResource;
import game.role.equip.recource.EquipSquareEnhanceResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/6/28 下午2:20
 */

@Component
public class EquipManager {

    @Autowired
    private EntityCacheService<Long, EquipStorageEnt> entEntityCache;

    @Static
    private Map<Long, EquipSquareEnhanceResource> squareEnhanceResources;

    @Static
    private Map<Long, EquipResource> equipResources;

    public EquipStorageEnt loadOrCreate(long playerId) {
        return entEntityCache.loadOrCreate(EquipStorageEnt.class, playerId, EquipStorageEnt::valueOf);
    }

    public void save(EquipStorageEnt ent) {
        entEntityCache.save(ent);
    }

    // 根据配置表configId获取资源文件
    public EquipSquareEnhanceResource getEquipEnhanceResource(long configId) {
        return squareEnhanceResources.get(configId);
    }

    public EquipResource getEquipResource(long configId) {
        for (EquipResource equipResource : equipResources.values()) {
            if (equipResource.getItemConfigId() == configId) {
                return equipResource;
            }
        }
        return null;
    }

}
