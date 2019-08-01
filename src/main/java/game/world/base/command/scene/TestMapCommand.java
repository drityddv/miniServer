package game.world.base.command.scene;

import java.util.Map;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;

/**
 * @author : ddv
 * @since : 2019/7/22 4:12 PM
 */

public class TestMapCommand extends AbstractSceneCommand {
    private Map<String, Object> param;

    public TestMapCommand(int mapId, long sceneId, Map<String, Object> param) {
        super(mapId, sceneId);
        this.param = param;
    }

    public static TestMapCommand valueOf(int mapId, long sceneId, Map<String, Object> param) {
        TestMapCommand command = new TestMapCommand(mapId, sceneId, param);
        return command;
    }

    @Override
    public void action() {
        AbstractMapHandler.getAbstractMapHandler(mapId).test(mapId, sceneId, param);
    }
}
