package game.miniMap.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.miniMap.visible.AbstractVisibleMapInfo;
import game.world.base.command.UpdatePositionCommand;

/**
 * 可移动的场景
 *
 * @author : ddv
 * @since : 2019/7/3 下午2:33
 */

public abstract class AbstractMovableMap<T extends AbstractVisibleMapInfo> extends AbstractScene {

    // 玩家accountId - T
    protected Map<String, T> accountIdToVisible = new HashMap<>();

    private UpdatePositionCommand updatePositionCommand;

    public AbstractMovableMap(int mapId) {
        super(mapId);
    }

    public T getVisibleObject(String accountId) {
        return accountIdToVisible.get(accountId);
    }

    public List<T> getVisibleObjects() {
        return new ArrayList<>(accountIdToVisible.values());
    }

    public void enter(String accountId, T object) {
        accountIdToVisible.put(accountId, object);
    }

    public void leave(String accountId) {
        accountIdToVisible.remove(accountId);
    }

    // 玩家是否存在于场景中
    public boolean isContainPlayer(String accountId) {
        return accountIdToVisible.containsKey(accountId);
    }

    // get and set
    public UpdatePositionCommand getUpdatePositionCommand() {
        return updatePositionCommand;
    }

    public void setUpdatePositionCommand(UpdatePositionCommand updatePositionCommand) {
        this.updatePositionCommand = updatePositionCommand;
    }

}
