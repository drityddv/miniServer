package game.world.neutral.neutralMap.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.FighterAccount;
import game.map.base.AbstractMovableMap;
import game.map.visible.impl.NpcVisibleInfo;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午3:57
 */

public class NeutralMapScene extends AbstractMovableMap<NeutralMapAccountInfo> {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapScene.class);

    // npc
    private Map<Long, NpcVisibleInfo> npcMap = new ConcurrentHashMap<>();;

    // 怪物
    private Map<Long, FighterAccount> monsterMap = new ConcurrentHashMap<>();;

    public NeutralMapScene(int mapId) {
        super(mapId);
    }

    public static NeutralMapScene valueOf(int mapId) {
        NeutralMapScene mapScene = new NeutralMapScene(mapId);
        return mapScene;
    }

    @Override
    public void enter(String accountId, NeutralMapAccountInfo object) {
        accountIdToVisible.putIfAbsent(accountId, object);
        logger.info("玩家[{}]进入中立场景[{}],场景内人数[{}]", accountId, mapId, accountIdToVisible.size());
    }

    @Override
    public void leave(String accountId) {
        super.leave(accountId);
        logger.info("玩家[{}]离开中立场景[{}],场景内人数[{}]", accountId, mapId, accountIdToVisible.size());
    }

    public Map<Long, NpcVisibleInfo> getNpcMap() {
        return npcMap;
    }

    @Override
    public NeutralMapAccountInfo getPlayerFighter(String accountId) {
        return accountIdToVisible.get(accountId);
    }
}
