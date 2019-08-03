package game.user.task.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.user.task.entity.TaskEnt;
import game.user.task.resource.TaskResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/8/2 11:44 AM
 */

@Component
public class TaskManager {

    private static TaskManager instance;

    @Autowired
    private EntityCacheService<String, TaskEnt> entityCacheService;

    @Static
    private Map<Long, TaskResource> taskResourceMap;

    public static TaskManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public TaskEnt loadOrCreate(String accountId) {
        return entityCacheService.loadOrCreate(TaskEnt.class, accountId, TaskEnt::valueOf);
    }

    public void save(TaskEnt ent) {
        entityCacheService.save(ent);
    }

    public TaskResource getTaskResource(long taskId) {
        return taskResourceMap.get(taskId);
    }
}
