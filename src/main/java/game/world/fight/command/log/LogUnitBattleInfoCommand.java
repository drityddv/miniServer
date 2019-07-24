package game.world.fight.command.log;

import game.base.executor.command.impl.scene.base.AbstractSceneCommand;
import game.base.fight.utils.BattleUtil;
import game.map.handler.AbstractMapHandler;
import game.map.visible.AbstractVisibleMapObject;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/18 2:29 PM
 */

public class LogUnitBattleInfoCommand extends AbstractSceneCommand {
    private Player player;
    private long unitId;

    public LogUnitBattleInfoCommand(Player player, long unitId) {
        super(player.getCurrentMapId());
        this.player = player;
        this.unitId = unitId;
    }

    public static LogUnitBattleInfoCommand valueOf(Player player, long unitId) {
        return new LogUnitBattleInfoCommand(player, unitId);
    }

    @Override
    public void action() {
        AbstractVisibleMapObject visibleMapInfo =
            AbstractMapHandler.getAbstractMapHandler(mapId).getUnit(mapId, unitId);

        if (visibleMapInfo == null) {
            return;
        }

        BattleUtil.logUnit(player, visibleMapInfo.getFighterAccount().getCreatureUnit());

    }
}
