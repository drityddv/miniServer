package game.miniMap.base;

import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.pvpunit.FighterAccount;

/**
 * @author : ddv
 * @since : 2019/7/2 上午10:20
 */

public abstract class AbstractScene {

    /**
     * 地图id
     */
    protected int mapId;
    /**
     * 场景战斗对象容器
     */
    private Map<String, FighterAccount> fighterAccountMap = new HashMap<>();
    /**
     * 定时器容器
     */
    private Map<Object, Object> commandMap = new HashMap<>();

    public AbstractScene(int mapId) {
        this.mapId = mapId;
    }

    public void clearFighterAccounts() {
        fighterAccountMap.clear();
    }

    public void fighterEnter(FighterAccount fighterAccount) {
        fighterAccountMap.put(fighterAccount.getAccountId(), fighterAccount);
    }

    public void fighterLeave(String accountId) {
        fighterAccountMap.remove(accountId);
    }

    // get and set
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public Map<String, FighterAccount> getFighterAccountMap() {
        return fighterAccountMap;
    }

    public void setFighterAccountMap(Map<String, FighterAccount> fighterAccountMap) {
        this.fighterAccountMap = fighterAccountMap;
    }

    public Map<Object, Object> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Object, Object> commandMap) {
        this.commandMap = commandMap;
    }

}
