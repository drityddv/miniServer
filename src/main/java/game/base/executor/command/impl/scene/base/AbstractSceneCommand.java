package game.base.executor.command.impl.scene.base;

import game.base.executor.command.AbstractCommand;
import game.miniMap.base.AbstractScene;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:12
 */

public abstract class AbstractSceneCommand extends AbstractCommand {
    protected String accountId;
    protected int mapId;
    protected AbstractScene scene;

    public AbstractSceneCommand(String accountId, int mapId) {
        this.accountId = accountId;
        this.mapId = mapId;
    }

    public AbstractSceneCommand(AbstractScene scene, String accountId) {
        this.accountId = accountId;
        this.scene = scene;
        this.mapId = scene.getMapId();
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public AbstractScene getScene() {
        return scene;
    }

    public void setScene(AbstractScene scene) {
        this.scene = scene;
    }

    /**
     * 执行逻辑
     */
    public abstract void action();
}
