package game.world.base.command.scene;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;

/**
 * 副本关闭命令
 *
 * @author : ddv
 * @since : 2019/7/31 4:29 PM
 */

public class InstanceCloseCommand extends AbstractSceneCommand {

    public InstanceCloseCommand(int mapId, long sceneId) {
        super(mapId, sceneId);
    }

    public static InstanceCloseCommand valueOf(int mapId, long sceneId) {
        InstanceCloseCommand command = new InstanceCloseCommand(mapId, sceneId);
        return command;
    }

    @Override
    public void action() {
        AbstractMapHandler.getAbstractMapHandler(mapId).closeInstance(mapId, sceneId);
    }
}
