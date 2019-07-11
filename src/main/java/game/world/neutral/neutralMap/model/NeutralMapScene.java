package game.world.neutral.neutralMap.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.fight.model.pvpunit.FighterAccount;
import game.map.base.AbstractMovableMap;
import game.map.visible.PlayerVisibleMapInfo;

/**
 * 中立地图
 *
 * @author : ddv
 * @since : 2019/7/3 下午3:57
 */

public class NeutralMapScene extends AbstractMovableMap<PlayerVisibleMapInfo> {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapScene.class);

    // 怪物
    private Map<Long, FighterAccount> monsterMap = new ConcurrentHashMap<>();

    public NeutralMapScene(int mapId) {
        super(mapId);
    }

    public static NeutralMapScene valueOf(int mapId) {
        NeutralMapScene mapScene = new NeutralMapScene(mapId);
        return mapScene;
    }

    public Map<Long, FighterAccount> getMonsterMap() {
        return monsterMap;
    }

}
