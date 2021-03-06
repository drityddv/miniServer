package game.role.skill.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.cache.EntityCacheService;
import game.base.skill.resource.SkillLevelResource;
import game.base.skill.resource.SkillResource;
import game.role.player.model.Player;
import game.role.skill.entity.SkillEnt;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/12 11:11 AM
 */
@Component
public class SkillManager {

    private static SkillManager instance;

    @Autowired
    private EntityCacheService<Long, SkillEnt> entityCacheService;

    @Static
    private Map<Long, SkillResource> skillResources;

    @Static
    private Map<Long, SkillLevelResource> skillLevelResources;

    public static SkillManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public SkillEnt loadOrCreate(Player player) {
        return entityCacheService.loadOrCreate(SkillEnt.class, player.getPlayerId(), SkillEnt::valueOf);
    }

    public void save(SkillEnt ent) {
        entityCacheService.save(ent);
    }

    public SkillResource getSkillResource(long configId) {
        return skillResources.get(configId);
    }

    public SkillLevelResource getSkillLevelResource(long configId) {
        return skillLevelResources.get(configId);
    }

    public SkillLevelResource getSkillLevelResource(long skillId, int level) {
        return skillLevelResources.values().stream().filter(
            skillLevelResource -> skillLevelResource.getSkillId() == skillId && skillLevelResource.getLevel() == level)
            .findAny().get();
    }
}
