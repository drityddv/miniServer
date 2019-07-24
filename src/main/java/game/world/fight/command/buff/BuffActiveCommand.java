package game.world.fight.command.buff;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.base.effect.model.buff.BaseCreatureBuff;
import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.map.handler.AbstractMapHandler;

/**
 * buff生效的作业逻辑
 *
 * @author : ddv
 * @since : 2019/7/17 4:07 PM
 */

public class BuffActiveCommand extends AbstractSceneCommand {
    private Logger logger = LoggerFactory.getLogger(BuffActiveCommand.class);

    private BaseCreatureBuff buff;
    // 剩余执行次数
    private int remainCount;

    public BuffActiveCommand(int mapId) {
        super(mapId);
    }

    public static BuffActiveCommand valueOf(BaseCreatureBuff buff, int mapId) {
        BuffActiveCommand command = new BuffActiveCommand(mapId);
        command.buff = buff;
//        command.remainCount = buff.getPeriod();
        return command;
    }

    @Override
    public void action() {
//        Map<Long, BaseCreatureBuff> buffEffects = AbstractMapHandler.getAbstractMapHandler(mapId).getBuffEffects(mapId);
//        buff = buffEffects.get(buff.getJobId());
//
//        // 调度时buff可能不在
//        if (buff != null) {
//            buff.active();
//            remainCount--;
//            if (remainCount == 0) {
//                logger.info("buff[{}] 执行周期结束 当前剩余次数[{}] 总共次数[{}]", buff.getJobId(), remainCount, buff.getPeriod());
//                buffEffects.remove(buff.getJobId());
//                buff.removeBuff();
//            }
//        }

    }

    public BaseCreatureBuff getBuff() {
        return buff;
    }

    public int getRemainCount() {
        return remainCount;
    }
}
