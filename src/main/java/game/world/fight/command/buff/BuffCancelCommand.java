package game.world.fight.command.buff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.impl.BaseCycleBuff;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;

/**
 * @author : ddv
 * @since : 2019/7/26 5:25 PM
 */

public class BuffCancelCommand extends AbstractSceneCommand {
    private Logger logger = LoggerFactory.getLogger(BuffCancelCommand.class);
    private BaseCreatureBuff buff;

    public BuffCancelCommand(int mapId) {
        super(mapId);
    }

    public static BuffCancelCommand valueOf(BaseCreatureBuff buff, int mapId) {
        BuffCancelCommand command = new BuffCancelCommand(mapId);
        command.buff = buff;
        return command;
    }

    @Override
    public void action() {
        if (buff instanceof BaseCycleBuff) {
            BaseCycleBuff baseCycleBuff = (BaseCycleBuff)buff;
            logger.info("取消buff[{}]", baseCycleBuff.getBuffId());
        }
        buff.tryCancel();
    }
}
