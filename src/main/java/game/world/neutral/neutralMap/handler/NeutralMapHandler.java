package game.world.neutral.neutralMap.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.miniMap.handler.IMovableMapHandler;
import game.miniMap.handler.AbstractMapHandler;
import game.miniMap.handler.ISceneMapHandler;
import game.miniMap.constant.MapGroupType;
import game.miniMap.model.Grid;
import game.user.player.model.Player;
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
    public void logout(Player player) {

    }

    @Override
    public void move(Player player, Grid targetGrid) {
        neutralMapService.doMove(player, player.getCurrentMapId(), targetGrid);
    }

    @Override
    public void realEnterMap(Player player, int mapId) {
        neutralMapService.enterMap(player, mapId);
    }

    @Override
    public void initFighterAccount(Player player, int mapId) {
        neutralMapService.initFighterAccount(player, mapId);
    }

    @Override
    public NeutralMapScene getEnterScene(String accountId, int mapId) {
        return neutralMapService.getEnterScene(accountId, mapId);
    }

    @Override
    public NeutralMapScene getCurrentScene(String accountId, int mapId) {
        return neutralMapService.getCurrentScene(accountId, mapId);
    }

    @Override
    public void doLogMap(Player player, int mapId) {
        neutralMapService.logMap(player, mapId);
    }
}
