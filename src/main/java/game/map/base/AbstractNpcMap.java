package game.map.base;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.map.visible.impl.NpcVisibleInfo;
import game.scene.npc.reource.NpcResource;

/**
 * @author : ddv
 * @since : 2019/7/11 3:29 PM
 */

public abstract class AbstractNpcMap extends AbstractScene {

    // npc
    private Map<Long, NpcVisibleInfo> npcMap = new ConcurrentHashMap<>();

    public AbstractNpcMap(int mapId) {
        super(mapId);
    }

    public void initNpc(List<NpcResource> npcResources) {
        npcResources.forEach(npcResource -> {
            NpcVisibleInfo npc = NpcVisibleInfo.valueOf(npcResource);
            npcMap.put(npc.getId(), npc);
        });
    }

    public Map<Long, NpcVisibleInfo> getNpcMap() {
        return npcMap;
    }

    public void setNpcMap(Map<Long, NpcVisibleInfo> npcMap) {
        this.npcMap = npcMap;
    }
}
