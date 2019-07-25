package game.world.base.command.scene;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractMapObject;
import game.map.visible.BaseAttackAbleMapObject;

/**
 * 复活还是顺着map找吧
 *
 * @author : ddv
 * @since : 2019/7/25 5:04 PM
 */

public class ReliveCommand extends AbstractSceneCommand {
    private long mapObjectId;

    public ReliveCommand(int mapId, long mapObjectId) {
        super(mapId);
        this.mapObjectId = mapObjectId;
    }

    public static ReliveCommand valueOf(int mapId, long mapObjectId) {
        ReliveCommand command = new ReliveCommand(mapId, mapObjectId);
        return command;
    }

    @Override
    public void action() {
        AbstractMapObject mapObject = AbstractMapHandler.getAbstractMapHandler(mapId).getUnit(mapId, mapObjectId);
        if (mapObject instanceof BaseAttackAbleMapObject) {
            BaseAttackAbleMapObject object = (BaseAttackAbleMapObject)mapObject;
            object.getFighterAccount().getCreatureUnit().relive();
        }
    }
}
