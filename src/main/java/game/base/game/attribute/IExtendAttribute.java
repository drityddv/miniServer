package game.base.game.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.user.player.model.Player;

/**
 * 额外的属性拓展
 *
 * @author : ddv
 * @since : 2019/6/27 上午11:48
 */

public interface IExtendAttribute {

    Map<AttributeType, List<IExtendAttribute>> EXTEND_ATTRIBUTES = new HashMap<>();

    /**
     * 计算额外的属性
     *
     * @param player
     * @param accmulateAttributes
     * @param effectedAttributeType
     * @return
     */
    static long computeAddValue(Player player, Map<AttributeType, Attribute> accmulateAttributes,
        AttributeType effectedAttributeType) {
        return 0;
    }
}
