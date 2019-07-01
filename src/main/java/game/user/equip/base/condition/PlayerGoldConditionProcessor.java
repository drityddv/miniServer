package game.user.equip.base.condition;

import game.user.player.model.Player;

import java.util.Map;

/**
 * 金币到达某个数目
 *
 * @author : ddv
 * @since : 2019/7/1 下午8:41
 */

public class PlayerGoldConditionProcessor extends AbstractConditionProcessor {

	public PlayerGoldConditionProcessor(Map<Object, Integer> conditionParams) {
		super(conditionParams);
	}

	@Override
    public boolean doVerify(Player player) {
        int gold = conditionParams.get("GOLD");
        return player.getGold() >= gold;
    }
}
