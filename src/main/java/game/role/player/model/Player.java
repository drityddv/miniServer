package game.role.player.model;

import game.base.executor.util.ExecutorUtils;
import game.base.game.attribute.model.PlayerAttributeContainer;
import game.base.object.AbstractCreature;
import game.map.handler.AbstractMapHandler;
import game.map.handler.ISceneMapHandler;
import game.role.equip.model.EquipStorage;
import game.role.player.entity.PlayerEnt;
import game.role.skill.model.SkillList;
import game.user.mapinfo.entity.MapInfoEnt;
import game.user.pack.model.Pack;
import game.user.task.model.TaskInfo;
import game.world.base.command.scene.FighterSyncCommand;
import game.world.fight.syncStrategy.BasePlayerSyncStrategy;
import spring.SpringContext;
import utils.snow.IdUtil;

/**
 * 做业务的对象
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:51
 */

public class Player extends AbstractCreature<Player> {

    private transient volatile boolean isLoaded = false;

    private String accountId;

    private long playerId;

    private long battleScore;
    /**
     * 0为男性 1为女性 2为无相关信息
     */
    private int sex;

    private int level;
    /**
     * 技能点 升级技能用 升级+1
     */
    private int skillPoint;
    /**
     * 黄金
     */
    private int gold;

    private volatile boolean changingMap;

    private PlayerAllianceInfo playerAllianceInfo;

    public Player() {}

    // 初始给1000黄金
    public static Player valueOf(String accountId) {
        Player player = new Player();
        player.accountId = accountId;
        player.playerId = IdUtil.getLongId();
        player.sex = 2;
        player.level = 1;
        player.skillPoint = 1;
        player.gold = 1000;
        player.changingMap = false;
        player.playerAllianceInfo = PlayerAllianceInfo.valueOf();
        player.setAttributeContainer(new PlayerAttributeContainer(player));
        return player;
    }

    public void fighterSync(BasePlayerSyncStrategy syncStrategy) {
        syncStrategy.init(this);
        try {
            AbstractMapHandler handler = AbstractMapHandler.getAbstractMapHandler(getCurrentMapId());
            ISceneMapHandler sceneMapHandler = (ISceneMapHandler)handler;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutorUtils.submit(FighterSyncCommand.valueOf(this, syncStrategy));
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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
        synchronized (this) {
            this.changingMap = changingMap;
        }
    }

    public int getCurrentMapId() {
        return SpringContext.getMapInfoService().getMapInfoEnt(this).getCurrentMapId();
    }

    public synchronized void setCurrentMapId(int mapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(this);
        mapInfoEnt.setCurrentMapId(mapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(this, mapInfoEnt);
    }

    public long getCurrentSceneId() {
        return SpringContext.getMapInfoService().getMapInfoEnt(this).getCurrentSceneId();
    }

    public void setCurrentSceneId(long sceneId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(this);
        mapInfoEnt.setCurrentSceneId(sceneId);
        SpringContext.getMapInfoService().saveMapInfoEnt(this, mapInfoEnt);
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }

    public void setLastMapId(int lastMapId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(this);
        mapInfoEnt.setLastMapId(lastMapId);
        SpringContext.getMapInfoService().saveMapInfoEnt(this, mapInfoEnt);
    }

    public SkillList getSkillList() {
        return SpringContext.getSkillService().getPlayerSkillList(this, false);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public long getBattleScore() {
        return battleScore;
    }

    public void setBattleScore(long battleScore) {
        this.battleScore = battleScore;
    }

    public TaskInfo getTaskInfo() {
        return SpringContext.getTaskService().getTaskInfo(this);
    }

    public PlayerAllianceInfo getPlayerAllianceInfo() {
        return playerAllianceInfo;
    }

    public void leaveAlliance(long currentAllianceId) {
        if (playerAllianceInfo.leaveAlliance(currentAllianceId)) {
            SpringContext.getPlayerService().save(this);
        }
    }

    public boolean changeAllianceId(long targetAllianceId) {
        boolean success;
        success = getPlayerAllianceInfo().changeAllianceId(targetAllianceId);
        if (success) {
            save();
        }
        return success;
    }

    public void save() {
        SpringContext.getPlayerService().save(this);
    }

}
