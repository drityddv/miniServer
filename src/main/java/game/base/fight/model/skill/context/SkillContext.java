package game.base.fight.model.skill.context;

import java.util.EnumMap;
import java.util.Map;

/**
 * 技能上下文
 *
 * @author : ddv
 * @since : 2019/7/15 9:22 PM
 */

public class SkillContext {

    private Map<SkillContextEnum, Object> keyToParam = new EnumMap<>(SkillContextEnum.class);
}
