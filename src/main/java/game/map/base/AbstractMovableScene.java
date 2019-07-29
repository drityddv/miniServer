package game.map.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.model.BaseBuff;
import game.base.buff.model.BaseCreatureBuff;
import game.base.fight.model.buff.PVPBuffComponent;
import game.base.fight.model.pvpunit.BaseCreatureUnit;
import game.map.model.Grid;
import game.map.npc.reource.NpcResource;
import game.map.visible.AbstractMapObject;
import game.map.visible.BaseAttackAbleMapObject;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.world.base.resource.CreatureResource;

/**
 * 可移动的地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:33
 */

public abstract class AbstractMovableScene<T extends AbstractMapObject> extends AbstractNpcScene {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMovableScene.class);

    // aoi模块
    protected MapAoiManager aoiManager;

    // 玩家unit
    protected Map<Long, T> playerMap = new HashMap<>();

    // 怪物unit
    protected Map<Long, MonsterMapObject> monsterMap = new HashMap<>();

    public AbstractMovableScene(int mapId) {
        super(mapId);
    }

    // 加载怪物
    public void initMonster(List<CreatureResource> creatureResources) {
        for (CreatureResource creatureResource : creatureResources) {
            MonsterMapObject monster = MonsterMapObject.valueOf(creatureResource);
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
    private void registerStaticUnitAoi(List<AbstractMapObject> objects) {
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
        PlayerMapObject object = (PlayerMapObject)playerMap.get(playerId);
        if (object == null) {
            return;
        }
        aoiManager.triggerMove(object, targetGrid);
    }

    public void leave(long playerId) {
        PlayerMapObject object = (PlayerMapObject)playerMap.get(playerId);
        if (object == null) {
            return;
        }

        BaseCreatureUnit creatureUnit = object.getFighterAccount().getCreatureUnit();
        PVPBuffComponent buffComponent = creatureUnit.getBuffComponent();
        Map<Long, BaseCreatureBuff> tempMap = new HashMap<>(buffComponent.getCastBuffMap());

        logger.info("玩家[{}]即将离开场景,清除释放buff容器 数量[{}]", object.getAccountId(),
            buffComponent.getCastBuffMap().values().size());
        tempMap.values().forEach(BaseBuff::forceCancel);

        tempMap = new HashMap<>(buffComponent.getBuffMap());
        logger.info("玩家[{}]即将离开场景,清除buff容器 数量[{}]", object.getAccountId(), buffComponent.getBuffMap().values().size());
        tempMap.values().forEach(BaseBuff::forceCancel);

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

    /**
     * 这个方法只会返回玩家和怪物
     *
     * @param targetIdList
     * @return
     */
    public List<AbstractMapObject> getMapObjects(List<Long> targetIdList) {
        List<AbstractMapObject> mapObjects = new ArrayList<>();
        if (targetIdList == null) {
            return mapObjects;
        }
        targetIdList.forEach(objectId -> {
            T t = playerMap.get(objectId);
            if (t == null) {
                t = (T)monsterMap.get(objectId);
            }
            if (t != null) {
                mapObjects.add(t);
            }
        });

        return mapObjects;
    }

    @Override
    public Map<Long, MonsterMapObject> getMonsterMap() {
        return monsterMap;
    }

    public List<AbstractMapObject> getAreaObjects(Grid grid) {
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
    public AbstractMapObject getPlayerFighter(long playerId) {
        return playerMap.get(playerId);
    }

    public void broadcast(Grid grid) {
        aoiManager.broadcast(grid);
    }

    public AbstractMapObject getMapObject(long objectId) {
        AbstractMapObject unit = monsterMap.get(objectId);
        if (unit == null) {
            unit = playerMap.get(objectId);
        }
        return unit;
    }

    public List<BaseCreatureUnit> getMapUnits(List<Long> targetIdList) {
        List<BaseCreatureUnit> unitList = new ArrayList<>();
        List<AbstractMapObject> mapObjects = getMapObjects(targetIdList);
        mapObjects.forEach(mapObject -> {
            BaseAttackAbleMapObject attackAbleMapObject = (BaseAttackAbleMapObject)mapObject;
            unitList.add(attackAbleMapObject.getFighterAccount().getCreatureUnit());
        });

        return unitList;
    }
}
