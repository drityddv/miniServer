package game.world.fight.event;

import ebus.model.IEvent;
import game.role.player.model.Player;
import game.world.base.resource.CreatureResource;

/**
 * @author : ddv
 * @since : 2019/8/2 11:57 AM
 */

public class KillMonsterEvent implements IEvent {
    private Player player;
    private int mapId;
    private CreatureResource creatureResource;

    public static KillMonsterEvent valueOf(Player player, int mapId, CreatureResource creatureResource) {
        KillMonsterEvent event = new KillMonsterEvent();
        event.player = player;
        event.mapId = mapId;
        event.creatureResource = creatureResource;
        return event;
    }

    public Player getPlayer() {
        return player;
    }

    public int getMapId() {
        return mapId;
    }

    public CreatureResource getCreatureResource() {
        return creatureResource;
    }
}
