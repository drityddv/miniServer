package game.world.mainCity.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.handler.AbstractMapHandler;
import game.map.handler.IMovableMapHandler;
import game.map.handler.ISceneMapHandler;
import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.mainCity.model.MainCityMapScene;
import game.world.mainCity.service.IMainCityService;

/**
 * 主城
 *
 * @author : ddv
 * @since : 2019/7/10 下午3:54
 */
@Component
public class MainCityMapHandler extends AbstractMapHandler
    implements IMovableMapHandler, ISceneMapHandler<MainCityMapScene> {

    @Autowired
    private IMainCityService mainCityService;

    @Override
    public void doLogMap(Player player, int mapId) {
        mainCityService.doLogMap(player, mapId);
    }

    @Override
    public void move(Player player, Grid targetGrid) {
        mainCityService.doMove(player, targetGrid);
    }

    @Override
    public MainCityMapScene getCurrentScene(Player player) {
        return mainCityService.getCurrentScene(player);
    }

    @Override
    public MapGroupType getGroupType() {
        return MapGroupType.MAIN_CITY;
    }

    @Override
    public void leaveMap(Player player) {
        mainCityService.leaveMap(player);
    }

    @Override
    public void realEnterMap(Player player, int mapId) {
        mainCityService.enterMap(player, mapId);
    }

    @Override
    public void heartBeat(int mapId) {
        // do nothing
    }

    @Override
    public void showAround(Player player) {
        move(player, getCurrentScene(player).getMapObject(player.getPlayerId()).getCurrentGrid());
    }

    @Override
    public AbstractMovableScene getMapScene(int mapId) {
        return mainCityService.getMapScene(mapId);
    }

}
