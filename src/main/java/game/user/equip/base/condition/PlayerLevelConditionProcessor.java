package game.user.equip.base.condition;

import game.user.player.model.Player;

import java.util.Map;

/**
 * 等级到达
 *
 * @author : ddv
 * @since : 2019/7/1 下午8:41
 */

public class PlayerLevelConditionProcessor extends AbstractConditionProcessor {

	public PlayerLevelConditionProcessor(Map<Object, Integer> conditionParams) {
		super(conditionParams);
	}

	@Override
    public boolean doVerify(Player player) {
        int level = conditionParams.get("LEVEL");
        return player.getLevel() >= level;
    }
}
