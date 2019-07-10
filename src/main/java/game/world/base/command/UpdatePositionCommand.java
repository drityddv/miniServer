package game.world.base.command;

import java.util.List;

import game.base.executor.command.impl.scene.base.AbstractSceneRateCommand;
import game.map.utils.VisibleUtil;
import game.map.visible.AbstractVisibleMapInfo;

/**
 * 地图移动命令
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:35
 */

public class UpdatePositionCommand extends AbstractSceneRateCommand {

    private List<? extends AbstractVisibleMapInfo> visibleObjects;

    public UpdatePositionCommand(int mapId, long delay, long period) {
        super(null, mapId, delay, period);
    }

    public static UpdatePositionCommand valueOf(int mapId, int delay, int period,
        List<? extends AbstractVisibleMapInfo> visibleObjects) {
        UpdatePositionCommand command = new UpdatePositionCommand(mapId, delay, period);
        command.visibleObjects = visibleObjects;
        return command;
    }

    @Override
    public void action() {
        if (visibleObjects != null) {
            VisibleUtil.doMove(visibleObjects);
        }
    }

    @Override
    public String getName() {
        return "UpdatePositionCommand";
    }
}
