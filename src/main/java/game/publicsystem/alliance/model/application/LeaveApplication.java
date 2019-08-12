// package game.publicsystem.alliance.model.application;
//
// import game.publicsystem.alliance.constant.OperationType;
// import game.publicsystem.alliance.model.Alliance;
// import game.role.player.model.Player;
// import spring.SpringContext;
//
/// **
// * 离开行会申请
// *
// * @author : ddv
// * @since : 2019/8/6 3:47 PM
// */
//
// public class LeaveApplication extends BaseAllianceApplication {
// public LeaveApplication(Player player, long allianceId) {
// super(player, OperationType.Leave_Alliance, allianceId);
// }
//
// public static LeaveApplication valueOf(Player player) {
// return new LeaveApplication(player, player.getPlayerAllianceInfo().getAllianceId());
// }
//
// @Override
// public boolean handler(boolean agreed) {
// if (expired) {
// return false;
// }
// Alliance alliance = SpringContext.getAllianceService().getAlliance(allianceId);
// if (agreed) {
// Player player = SpringContext.getPlayerService().getPlayerByAccountId(accountId);
// if (player.getPlayerAllianceInfo().leaveAlliance(allianceId)) {
// alliance.getMemberSet().remove(playerId);
// alliance.getApplicationMap().get(getOperationType()).remove(playerId);
// }
//
// SpringContext.getAllianceService().sendAllianceInfo(player);
// }
//
// expired = true;
// return true;
// }
// }
