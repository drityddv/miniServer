package game.world.neutral.neutralMap.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.buff.model.BaseCreatureBuff;
import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.handler.AbstractMapHandler;
import game.map.handler.IMovableMapHandler;
import game.map.handler.ISceneMapHandler;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.role.player.model.Player;
import game.world.neutral.neutralMap.model.NeutralMapScene;
import game.world.neutral.neutralMap.service.INeutralMapService;

/**
 * @author : ddv
 * @since : 2019/7/3 下午4:58
 */
@Component
public class NeutralMapHandler extends AbstractMapHandler
    implements IMovableMapHandler, ISceneMapHandler<NeutralMapScene> {

    @Autowired
    private INeutralMapService neutralMapService;

    @Override
    public MapGroupType getGroupType() {
        return MapGroupType.NEUTRAL_MAP;
    }

    @Override
    public void leaveMap(Player player) {
        neutralMapService.leaveMap(player);
    }

    @Override
    public void move(Player player, Grid targetGrid) {
        neutralMapService.doMove(player, targetGrid);
    }

    @Override
    public void realEnterMap(Player player, int mapId) {
        neutralMapService.enterMap(player, mapId);
    }

    @Override
    public NeutralMapScene getCurrentScene(Player player) {
        return neutralMapService.getCurrentScene(player);
    }

    @Override
    public void doLogMap(Player player, int mapId) {
        neutralMapService.logMap(player, mapId);
    }

    @Override
    public boolean canEnterMap(Player player, int mapId) {
        return neutralMapService.canEnterMap(player, mapId);
    }

    @Override
    public void heartBeat(int mapId) {
        neutralMapService.heartBeat(mapId);
    }

    @Override
    public Map<Long, PlayerMapObject> getPlayerObjects(int mapId) {
        return neutralMapService.getMapScene(mapId).getPlayerMap();
    }

    @Override
    public Map<Long, MonsterMapObject> getMonsterObjects(int mapId) {
        return neutralMapService.getMapScene(mapId).getMonsterMap();
    }

    @Override
    public Map<Long, BaseCreatureBuff> getBuffEffects(int mapId) {
        return neutralMapService.getMapScene(mapId).getBuffEffectMap();
    }

    @Override
    public AbstractMovableScene getMapScene(int mapId) {
        return neutralMapService.getMapScene(mapId);
    }

    @Override
    public void test(int mapId, Map<String, Object> param) {
        neutralMapService.test(mapId, param);
    }

    @Override
    public void broadcast(int mapId, Grid currentGrid) {
        neutralMapService.broadcast(mapId, currentGrid);
    }
}
