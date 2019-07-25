package game.world.fight.command.buff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffTriggerPoint;
import game.base.buff.model.impl.BaseCycleBuff;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;

/**
 * buff生效的作业逻辑
 *
 * @author : ddv
 * @since : 2019/7/17 4:07 PM
 */

public class BuffActiveCommand extends AbstractSceneCommand {
    private Logger logger = LoggerFactory.getLogger(BuffActiveCommand.class);

    private BaseCreatureBuff buff;

    public BuffActiveCommand(int mapId) {
        super(mapId);
    }

    public static BuffActiveCommand valueOf(BaseCreatureBuff buff, int mapId) {
        BuffActiveCommand command = new BuffActiveCommand(mapId);
        command.buff = buff;
        return command;
    }

    @Override
    public void action() {
        if (buff instanceof BaseCycleBuff) {
            BaseCycleBuff baseCycleBuff = (BaseCycleBuff)buff;
            logger.info("period[{}] remainCount[{}]", baseCycleBuff.getPeriodCount(),
                baseCycleBuff.getRemainCount());
        }
        logger.info("buff调度命令 buff[{} {}]", buff.getBuffId(), buff.getBuffResource().getBuffName());
        buff.triggerBuff(BuffTriggerPoint.Schedule_Active);
        buff.tryCancel();
    }

    public BaseCreatureBuff getBuff() {
        return buff;
    }

}
