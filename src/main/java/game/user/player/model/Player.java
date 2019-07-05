package game.user.player.model;

import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.object.AbstractCreature;
import game.user.equip.model.EquipStorage;
import game.user.mapinfo.entity.MapInfoEnt;
import game.user.pack.model.Pack;
import game.user.player.entity.PlayerEnt;
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

    private int level;
    /**
     * 黄金
     */
    private int gold;

    /**
     * 上次修改切图的操作类型0:无 1:离开地图 2:进入地图
     */
    // private volatile int lastChangeType;

    private volatile boolean changingMap;

    // private Lock lock = new ReentrantLock();

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

    // FIXME 这里有可能离开地图会覆盖掉进入地图的操作 要修
    public void setCurrentMapId(int mapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(this);
        mapInfoEnt.setCurrentMapId(mapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(this, mapInfoEnt);
    }

}
