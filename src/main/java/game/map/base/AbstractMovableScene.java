package game.map.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.model.Grid;
import game.map.npc.reource.NpcResource;
import game.map.visible.AbstractVisibleMapObject;
import game.map.visible.PlayerVisibleMapObject;
import game.map.visible.impl.MonsterVisibleMapObject;
import game.world.base.resource.CreatureResource;

/**
 * 可移动的地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:33
 */

public abstract class AbstractMovableScene<T extends AbstractVisibleMapObject> extends AbstractNpcScene {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMovableScene.class);

    // aoi模块
    protected MapAoiManager aoiManager;

    // 玩家unit
    protected Map<Long, T> playerMap = new HashMap<>();

    // 怪物unit
    protected Map<Long, MonsterVisibleMapObject> monsterMap = new HashMap<>();

    public AbstractMovableScene(int mapId) {
        super(mapId);
    }

    // 加载怪物
    public void initMonster(List<CreatureResource> creatureResources) {
        for (CreatureResource creatureResource : creatureResources) {
            MonsterVisibleMapObject monster = MonsterVisibleMapObject.valueOf(creatureResource);
            monsterMap.put(monster.getId(), monster);
        }
        registerStaticUnitAoi(new ArrayList<>(monsterMap.values()));
    }

    @Override
    public void initNpc(List<NpcResource> npcResources) {
        super.initNpc(npcResources);
        registerStaticUnitAoi(new ArrayList<>(npcMap.values()));

    }

    // 加载地图单位到广播中心
    private void registerStaticUnitAoi(List<AbstractVisibleMapObject> objects) {
        objects.forEach(object -> {
            aoiManager.registerUnits(object);
        });
    }

    public void enter(long playerId, T object) {
        T absent = playerMap.putIfAbsent(playerId, object);
        if (absent == null) {
            playerMap.put(object.getId(), object);
            aoiManager.triggerEnter(object);
            logger.info("玩家[{}]进入场景[{}],场景内人数[{}]", playerId, mapId, playerMap.size());
        }
    }

    public void move(long playerId, Grid targetGrid) {
        PlayerVisibleMapObject object = (PlayerVisibleMapObject)playerMap.get(playerId);
        if (object == null) {
            return;
        }
        aoiManager.triggerMove(object, targetGrid);
    }

    public void leave(long playerId) {
        PlayerVisibleMapObject object = (PlayerVisibleMapObject)playerMap.get(playerId);
        if (object == null) {
            return;
        }
        playerMap.remove(playerId);
        aoiManager.triggerLeave(object);
        logger.info("玩家[{}]离开场景[{}],场景内人数[{}]", playerId, mapId, playerMap.size());
    }

    public List<BaseCreatureUnit> getCreatureUnits(List<Grid> gridList) {
        return aoiManager.getCreatureUnits(gridList);
    }

    @Override
    public Map<Long, T> getPlayerMap() {
        return playerMap;
    }

    @Override
    public Map<Long, MonsterVisibleMapObject> getMonsterMap() {
        return monsterMap;
    }

    public List<AbstractVisibleMapObject> getAreaObjects(Grid grid) {
        return aoiManager.getAreaObjects(grid);
    }

    public List<T> getVisibleObjects() {
        return new ArrayList<>(playerMap.values());
    }

    // 玩家是否存在于场景中
    public boolean isContainPlayer(long playerId) {
        return playerMap.containsKey(playerId);
    }

    @Override
    public AbstractVisibleMapObject getPlayerFighter(long playerId) {
        return playerMap.get(playerId);
    }

}
