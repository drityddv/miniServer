package game.map.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.map.visible.AbstractVisibleMapInfo;

/**
 * 可移动的场景
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:33
 */

public abstract class AbstractMovableMap<T extends AbstractVisibleMapInfo> extends AbstractNpcMap {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMovableMap.class);

    // 玩家accountId - T
    protected Map<Long, T> playerMap = new HashMap<>();

    public AbstractMovableMap(int mapId) {
        super(mapId);
    }

    public T getVisibleObject(String accountId) {
        return playerMap.get(accountId);
    }

    public List<T> getVisibleObjects() {
        return new ArrayList<>(playerMap.values());
    }

    public void enter(long playerId, T object) {
        playerMap.putIfAbsent(playerId, object);
        logger.info("玩家[{}]进入中立场景[{}],场景内人数[{}]", playerId, mapId, playerMap.size());
    }

    public void leave(long playerId) {
        playerMap.remove(playerId);
        logger.info("玩家[{}]离开中立场景[{}],场景内人数[{}]", playerId, mapId, playerMap.size());
    }

    // 玩家是否存在于场景中
    public boolean isContainPlayer(long playerId) {
        return playerMap.containsKey(playerId);
    }

    public Map<Long, T> getPlayerMap() {
        return playerMap;
    }

    @Override
    public AbstractVisibleMapInfo getPlayerFighter(long playerId) {
        return playerMap.get(playerId);
    }
}
