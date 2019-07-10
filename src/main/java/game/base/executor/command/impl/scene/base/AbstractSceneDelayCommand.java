package game.base.executor.command.impl.scene.base;

import game.map.base.AbstractScene;

/**
 * @author : ddv
 * @since : 2019/7/2 下午12:17
 */

public abstract class AbstractSceneDelayCommand extends AbstractSceneCommand {

    private long delay;

    public AbstractSceneDelayCommand(String accountId, int mapId, long delay) {
        super(accountId, mapId);
        this.delay = delay;
    }

    public AbstractSceneDelayCommand(AbstractScene scene, String accountId, long delay) {
        super(scene, accountId);
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
