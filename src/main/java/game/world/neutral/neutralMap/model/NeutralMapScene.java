package game.world.neutral.neutralMap.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.miniMap.base.AbstractMovableMap;
import game.miniMap.visible.impl.NpcVisibleInfo;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午3:57
 */

public class NeutralMapScene extends AbstractMovableMap<NeutralMapAccountInfo> {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapScene.class);

    // npc
    private Map<Long, NpcVisibleInfo> npcMap;

    public NeutralMapScene(int mapId) {
        super(mapId);
    }

    // FIXME 这里内网的npc用的是并发map ??? 可能会在玩家线程直接拉这个map去遍历
    public static NeutralMapScene valueOf(int mapId) {
        NeutralMapScene mapScene = new NeutralMapScene(mapId);
        mapScene.npcMap = new ConcurrentHashMap<>();
        return mapScene;
    }

    @Override
    public void enter(String accountId, NeutralMapAccountInfo object) {
        // FIXME 这边内网地图会用心跳清除掉玩家,逻辑是如果玩家心跳清除之前回来了 继续用旧的
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
}
