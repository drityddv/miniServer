package game.base.executor.command.impl.scene.base;

import game.miniMap.base.AbstractScene;

/**
 * @author : ddv
 * @since : 2019/7/2 下午12:15
 */

public abstract class AbstractSceneRateCommand extends AbstractSceneDelayCommand {

    /**
     * 周期
     */
    private long period;

    public AbstractSceneRateCommand(String accountId, int mapId, long delay, long period) {
        super(accountId, mapId, delay);
        this.period = period;
    }

    public AbstractSceneRateCommand(AbstractScene scene, String accountId, long delay, long period) {
        super(scene, accountId, delay);
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
