package game.map.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.map.npc.reource.NpcResource;
import game.map.visible.impl.NpcObject;

/**
 * @author : ddv
 * @since : 2019/7/11 3:29 PM
 */

public abstract class AbstractNpcScene extends AbstractScene {

    // npc
    protected Map<Long, NpcObject> npcMap = new HashMap<>();

    public AbstractNpcScene(int mapId) {
        super(mapId);
    }

    public void initNpc(List<NpcResource> npcResources) {
        npcResources.forEach(npcResource -> {
            NpcObject npc = NpcObject.valueOf(npcResource);
            npcMap.put(npc.getId(), npc);
        });
    }

    public Map<Long, NpcObject> getNpcMap() {
        return npcMap;
    }

}
