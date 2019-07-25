package game.world.mainCity.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.map.base.AbstractMovableScene;
import game.map.constant.MapGroupType;
import game.map.model.Grid;
import game.map.visible.PlayerMapObject;
import game.map.visible.impl.NpcObject;
import game.role.player.model.Player;
import game.world.base.resource.MiniMapResource;
import game.world.base.service.WorldManager;
import game.world.mainCity.model.MainCityMapInfo;
import game.world.mainCity.model.MainCityMapScene;
import game.world.utils.MapUtil;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/10 下午3:29
 */
@Component
public class MainCityService implements IMainCityService {

    private static final Logger logger = LoggerFactory.getLogger(MainCityService.class);

    @Autowired
    private WorldManager worldManager;

    @Autowired
    private MainCityManager mainCityManager;

    @Override
    public void init() {
        initMapInfo();
    }

    @Override
    public void enterMap(Player player, int mapId) {
        MainCityMapInfo mapInfo = getMapInfo(mapId);
        MainCityMapScene scene = mapInfo.getMapScene();
        MiniMapResource miniMapResource = mapInfo.getMiniMapResource();

        PlayerMapObject visibleMapInfo = PlayerMapObject.valueOf(player, mapId);
        visibleMapInfo.init(miniMapResource.getBornX(), miniMapResource.getBornY());

        scene.enter(player.getPlayerId(), visibleMapInfo);
    }

    @Override
    public void leaveMap(Player player) {
        getScene(player.getCurrentMapId()).leave(player.getPlayerId());
    }

    @Override
    public void doMove(Player player, Grid targetGrid) {
        MainCityMapScene mapScene = getScene(player.getCurrentMapId());
        mapScene.move(player.getPlayerId(), targetGrid);

    }

    @Override
    public MainCityMapScene getCurrentScene(Player player) {
        return null;
    }

    @Override
    public void doLogMap(Player player, int mapId) {
        MainCityMapInfo mapCommonInfo = mainCityManager.getMainCityMapInfo(mapId);
        MainCityMapScene mapScene = mapCommonInfo.getMapScene();
        List<PlayerMapObject> visibleObjects = mapScene.getVisibleObjects();
        Collection<NpcObject> npcList = mapScene.getNpcMap().values();

        MapUtil.log(player, mapScene, visibleObjects, npcList, null, null);
    }

    @Override
    public AbstractMovableScene getMapScene(int mapId) {
        return getScene(mapId);
    }

    private void initMapInfo() {
        List<MiniMapResource> mainCityMapResource =
            worldManager.getMapResourcesByGroup(MapGroupType.MAIN_CITY.getGroupId());
        mainCityMapResource.forEach(mapResource -> {
            MainCityMapInfo mapInfo = MainCityMapInfo.valueOf(mapResource);
            mapInfo.getMapScene().initNpc(SpringContext.getCreatureManager().getNpcByMapId(mapInfo.getMapId()));
            mainCityManager.addMainCityMapInfo(mapInfo);

        });
    }

    private MainCityMapInfo getMapInfo(int mapId) {
        return mainCityManager.getMainCityMapInfo(mapId);
    }

    private MainCityMapScene getScene(int mapId) {
        return getMapInfo(mapId).getMapScene();
    }

}
