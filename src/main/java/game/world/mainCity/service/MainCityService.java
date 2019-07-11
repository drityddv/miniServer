package game.world.mainCity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.map.model.Grid;
import game.role.player.model.Player;
import game.world.mainCity.model.MainCityMapScene;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:29
 */
@Component
public class MainCityService implements IMainCityService {

    private static final Logger logger = LoggerFactory.getLogger(MainCityService.class);

    @Override
    public void init() {

    }

    @Override
    public void enterMap(Player player, int mapId) {

    }

    @Override
    public void leaveMap(Player player) {

    }

    @Override
    public void doMove(Player player, int mapId, Grid targetGrid) {

    }

    @Override
    public MainCityMapScene getCurrentScene(Player player) {
        return null;
    }

    @Override
    public void doLogMap(Player player, int mapId) {

    }
}
