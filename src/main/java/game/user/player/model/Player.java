package game.user.player.model;

import game.base.executor.util.ExecutorUtils;
import game.base.fight.model.pvpunit.FighterAccount;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.object.AbstractCreature;
import game.miniMap.base.AbstractScene;
import game.miniMap.handler.AbstractMapHandler;
import game.miniMap.handler.ISceneMapHandler;
import game.scene.fight.syncStrategy.BasePlayerSyncStrategy;
import game.user.equip.model.EquipStorage;
import game.user.mapinfo.entity.MapInfoEnt;
import game.user.pack.model.Pack;
import game.user.player.entity.PlayerEnt;
import game.world.base.command.FighterSyncCommand;
import spring.SpringContext;
import utils.IdUtil;

/**
 * 做业务的对象
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:51
 */

public class Player extends AbstractCreature<Player> {

    private String accountId;

    private long playerId;

    /**
     * 0为男性 1为女性 2为无相关信息
     */
    private int sex;

    private int level;
    /**
     * 黄金
     */
    private int gold;

    private volatile boolean changingMap;

    private Player() {}

    // 初始给1000黄金
    public static Player valueOf(String accountId) {
        Player player = new Player();
        player.accountId = accountId;
        player.playerId = IdUtil.getLongId();
        player.level = 1;
        player.gold = 1000;
        player.changingMap = false;
        player.setAttributeContainer(new PlayerAttributeContainer(player));
        return player;
    }

    public FighterAccount getFight() {
        return SpringContext.getFightService().initForPlayer(this);
    }

    public void fighterSync(BasePlayerSyncStrategy syncStrategy) {
        syncStrategy.init(this);
        AbstractScene currentScene = null;
        try {
            AbstractMapHandler handler = AbstractMapHandler.getAbstractMapHandler(getCurrentMapId());
            ISceneMapHandler sceneMapHandler = (ISceneMapHandler)handler;
            currentScene = sceneMapHandler.getCurrentScene(accountId, getCurrentMapId());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ExecutorUtils.submit(this, FighterSyncCommand.valueOf(this.getAccountId(), syncStrategy, currentScene));
    }

    @Override
    public PlayerAttributeContainer getAttributeContainer() {
        return (PlayerAttributeContainer)super.getAttributeContainer();
    }

    public Pack getPack() {
        return SpringContext.getPackService().getPlayerPack(this, false);
    }

    public PlayerEnt getPlayerEnt() {
        return SpringContext.getPlayerService().getPlayerEnt(this);
    }

    public EquipStorage getEquipStorage() {
        return SpringContext.getEquipService().getEquipStorage(this);
    }

    // get and set
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getLevel() {
        return level;
    }

    public int setLevel(int level) {
        this.level = level;
        return this.level;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean isChangingMap() {
        return changingMap;
    }

    public void setChangingMap(boolean changingMap) {
        this.changingMap = changingMap;
    }

    public int getCurrentMapId() {
        return SpringContext.getMapInfoService().getMapInfoEnt(this).getCurrentMapId();
    }

    public void setCurrentMapId(int mapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(this);
        mapInfoEnt.setCurrentMapId(mapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(this, mapInfoEnt);
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
