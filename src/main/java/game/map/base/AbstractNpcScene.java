package game.map.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.map.npc.reource.NpcResource;
import game.map.visible.impl.NpcVisibleObject;

/**
 * @author : ddv
 * @since : 2019/7/11 3:29 PM
 */

public abstract class AbstractNpcScene extends AbstractScene {

    // npc
    protected Map<Long, NpcVisibleObject> npcMap = new HashMap<>();

    public AbstractNpcScene(int mapId) {
        super(mapId);
    }

    public void initNpc(List<NpcResource> npcResources) {
        npcResources.forEach(npcResource -> {
            NpcVisibleObject npc = NpcVisibleObject.valueOf(npcResource);
            npcMap.put(npc.getId(), npc);
        });
    }

    public Map<Long, NpcVisibleObject> getNpcMap() {
        return npcMap;
    }

    public void setNpcMap(Map<Long, NpcVisibleObject> npcMap) {
        this.npcMap = npcMap;
    }
}
