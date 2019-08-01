package game.world.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.map.npc.reource.NpcResource;
import game.world.base.resource.CreatureResource;
import game.world.instance.resource.HatchResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/5 上午11:46
 */
@Component
public class CreatureManager {

    private static CreatureManager instance;

    @Static
    private Map<Integer, NpcResource> npcResources;

    @Static
    private Map<Integer, CreatureResource> creatureResources;

    @Static
    private Map<Long, HatchResource> hatchResourceMap;

    public static CreatureManager getInstance() {
        return instance;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public NpcResource getNpcResource(int configId) {
        return npcResources.get(configId);
    }

    public CreatureResource getCreatureResource(int configId) {
        return creatureResources.get(configId);
    }

    public List<CreatureResource> getCreatureResourceByMapId(int mapId) {
        List<CreatureResource> creatureResourceList = new ArrayList<>();
        creatureResourceList.addAll(creatureResources.values().stream()
            .filter(creatureResource -> creatureResource.getMapId() == mapId).collect(Collectors.toList()));
        return creatureResourceList;
    }

    public List<NpcResource> getNpcByMapId(int mapId) {
        List<NpcResource> npcResourceList = new ArrayList<>();
        npcResourceList.addAll(npcResources.values().stream().filter(npcResource -> npcResource.getMapId() == mapId)
            .collect(Collectors.toList()));
        return npcResourceList;
    }

    public HatchResource getHatchResource(long configId) {
        return hatchResourceMap.get(configId);
    }

}
