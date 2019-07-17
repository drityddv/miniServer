package game.world.fight.command;

import java.util.Map;

import game.base.effect.model.BaseBuffEffect;
import game.base.executor.command.impl.scene.base.AbstractSceneRateCommand;
import game.map.handler.AbstractMapHandler;

/**
 * @author : ddv
 * @since : 2019/7/17 4:07 PM
 */

public class BuffActiveCommand extends AbstractSceneRateCommand {

    private long buffId;

    public BuffActiveCommand(int mapId, long delay, long period) {
        super(mapId, delay, period);
    }

    public static BuffActiveCommand valueOf(long buffId, long delay, long period, int mapId) {
        BuffActiveCommand command = new BuffActiveCommand(mapId, delay, period);
        return command;
    }

    @Override
    public void action() {
        Map<Long, BaseBuffEffect> buffEffects = AbstractMapHandler.getAbstractMapHandler(mapId).getBuffEffects(mapId);
        BaseBuffEffect buffEffect = buffEffects.get(buffId);
        // 调度时buff可能不在
        if (buffEffect != null) {
            buffEffect.active();
        }
    }
}
