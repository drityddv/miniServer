package game.world.base.command.scene;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.base.AbstractMovableScene;
import game.map.handler.AbstractMapHandler;
import game.world.instance.base.model.BaseInstanceMapScene;

/**
 * 副本定时切阶段命令
 *
 * @author : ddv
 * @since : 2019/8/1 3:16 PM
 */

public class InstanceChangeStageCommand extends AbstractSceneCommand {
    public InstanceChangeStageCommand(int mapId, long sceneId) {
        super(mapId, sceneId);
    }

    public static InstanceChangeStageCommand valueOf(int mapId, long sceneId) {
        InstanceChangeStageCommand command = new InstanceChangeStageCommand(mapId, sceneId);
        return command;
    }

    @Override
    public void action() {
        AbstractMovableScene mapScene = AbstractMapHandler.getAbstractMapHandler(mapId).getMapScene(mapId, sceneId);
        BaseInstanceMapScene baseInstanceMapScene = (BaseInstanceMapScene)mapScene;

        baseInstanceMapScene.changeStage();
    }
}
