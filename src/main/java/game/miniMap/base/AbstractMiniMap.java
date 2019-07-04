// package game.miniMap.base;
//
// import java.util.HashMap;
// import java.util.Map;
//
// import game.base.fight.pvpunit.FighterAccount;
//
/// **
// * 抽象地图
// *
// * @author : ddv
// * @since : 2019/7/3 下午12:28
// */
//
// public abstract class AbstractMiniMap {
//
// /**
// * 地图id
// */
// protected int mapId;
// /**
// * 场景战斗对象容器
// */
// private Map<String, FighterAccount> fighterAccountMap = new HashMap<>();
//
// /**
// * 定时器容器
// */
// private Map<Object, Object> commandMap = new HashMap<>();
// /**
// * 场景容器组件
// */
// private SceneComponentInfo sceneComponentInfo = new SceneComponentInfo();
//
// public AbstractMiniMap(int mapId) {
// this.mapId = mapId;
// }
//
// public void clearFighterAccounts() {
// fighterAccountMap.clear();
// }
//
// public void fighterEnter(FighterAccount fighterAccount) {
// fighterAccountMap.put(fighterAccount.getAccountId(), fighterAccount);
// }
//
// public void fighterLeave(String accountId) {
// fighterAccountMap.remove(accountId);
// }
//
// // get and set
// public int getMapId() {
// return mapId;
// }
//
// public void setMapId(int mapId) {
// this.mapId = mapId;
// }
//
// public Map<String, FighterAccount> getFighterAccountMap() {
// return fighterAccountMap;
// }
//
// public void setFighterAccountMap(Map<String, FighterAccount> fighterAccountMap) {
// this.fighterAccountMap = fighterAccountMap;
// }
//
// public Map<Object, Object> getCommandMap() {
// return commandMap;
// }
//
// public void setCommandMap(Map<Object, Object> commandMap) {
// this.commandMap = commandMap;
// }
//
// public SceneComponentInfo getSceneComponentInfo() {
// return sceneComponentInfo;
// }
//
// public void setSceneComponentInfo(SceneComponentInfo sceneComponentInfo) {
// this.sceneComponentInfo = sceneComponentInfo;
// }
// }
