package game.world.neutral.neutralMap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.base.AbstractMovableMap;
import game.map.visible.PlayerVisibleMapInfo;
import game.map.visible.impl.MonsterVisibleMapInfo;
import game.world.base.resource.CreatureResource;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午3:57
 */

public class NeutralMapScene extends AbstractMovableMap<PlayerVisibleMapInfo> {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapScene.class);

    // 怪物
    private Map<Long, MonsterVisibleMapInfo> monsterMap = new ConcurrentHashMap<>();

    public NeutralMapScene(int mapId) {
        super(mapId);
    }

    public static NeutralMapScene valueOf(int mapId) {
        NeutralMapScene mapScene = new NeutralMapScene(mapId);
        return mapScene;
    }

    public List<MonsterVisibleMapInfo> getMonsters() {
        return new ArrayList<>(monsterMap.values());
    }

    public Map<Long, MonsterVisibleMapInfo> getMonsterMap() {
        return monsterMap;
    }

    public void initMonster(List<CreatureResource> creatureResources) {
        for (CreatureResource creatureResource : creatureResources) {
            MonsterVisibleMapInfo monster = MonsterVisibleMapInfo.valueOf(creatureResource);
            monsterMap.put(monster.getId(), monster);
        }
    }
}
