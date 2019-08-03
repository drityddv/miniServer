package game.user.task.model;

import game.role.player.model.Player;
import game.world.base.resource.CreatureResource;

/**
 * @author : ddv
 * @since : 2019/8/2 3:46 PM
 */

public class TaskEventParam {
    private Player player;
    private int mapId;
    private CreatureResource creatureResource;
    private int value;

    public static TaskEventParam valueOf() {
        return new TaskEventParam();
    }

    public static TaskEventParam valueOf(Player player) {
        TaskEventParam eventParam = new TaskEventParam();
        eventParam.player = player;
        return eventParam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CreatureResource getCreatureResource() {
        return creatureResource;
    }

    public void setCreatureResource(CreatureResource creatureResource) {
        this.creatureResource = creatureResource;
    }
}
