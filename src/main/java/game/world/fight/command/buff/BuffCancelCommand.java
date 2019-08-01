package game.world.fight.command.buff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.impl.BaseScheduleBuff;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;

/**
 * @author : ddv
 * @since : 2019/7/26 5:25 PM
 */

public class BuffCancelCommand extends AbstractSceneCommand {
    private Logger logger = LoggerFactory.getLogger(BuffCancelCommand.class);
    private BaseCreatureBuff buff;

    public BuffCancelCommand(int mapId, long sceneId) {
        super(mapId, sceneId);
    }

    public static BuffCancelCommand valueOf(BaseCreatureBuff buff, int mapId, long sceneId) {
        BuffCancelCommand command = new BuffCancelCommand(mapId, sceneId);
        command.buff = buff;
        return command;
    }

    @Override
    public void action() {
        if (buff instanceof BaseScheduleBuff) {
            BaseScheduleBuff baseScheduleBuff = (BaseScheduleBuff)buff;
            logger.info("取消buff[{}]", baseScheduleBuff.getBuffId());
        }
        buff.tryCancel();
    }
}
