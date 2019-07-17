package game.base.executor.command.impl.scene.base;

import game.base.executor.command.AbstractCommand;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:12
 */

public abstract class AbstractSceneCommand extends AbstractCommand {
    protected int mapId;

    public AbstractSceneCommand(int mapId) {
        this.mapId = mapId;
    }

    @Override
    public Integer getKey() {
        return mapId;
    }

    @Override
    public void active() {
        action();
    }

    @Override
    public int modIndex(int poolSize) {
        return Math.abs(getKey() % poolSize);
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    /**
     * 执行逻辑
     */
    public abstract void action();
}
