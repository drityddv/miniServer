package game.base.fight.model.skill.action.impl;

import game.base.fight.model.pvpunit.PlayerUnit;
import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/16 12:14 PM
 */

public class PlayerSkillAction {
    private Player player;
    private PlayerUnit unit;
    private long skillId;

    public PlayerSkillAction(Player player, PlayerUnit unit) {
        this.player = player;
        this.unit = unit;
    }
}
