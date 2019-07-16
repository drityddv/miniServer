package game.base.skill.constant;

import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.model.skill.action.handler.IActionHandler;
import game.base.fight.model.skill.action.handler.impl.DefaultAttackHandler;

/**
 * @author : ddv
 * @since : 2019/7/15 8:35 PM
 */

public enum SkillType {
    /**
     * 普通攻击[立即生效 无其他效果]
     */
    DEFAULT_ATTACK(1, DefaultAttackHandler.class),;

    public static Map<SkillType, BaseActionHandler> TYPE_TO_HANDLER = new HashMap<>(SkillType.values().length);
    public static Map<Integer, SkillType> ID_TO_TYPE = new HashMap<>(SkillType.values().length);

    static {
        for (SkillType type : SkillType.values()) {
            TYPE_TO_HANDLER.put(type, type.getActionHandler());
            ID_TO_TYPE.put(type.id, type);
        }
    }

    private int id;
    // 技能动作处理器
    private Class<? extends BaseActionHandler> actionHandlerClazz;
    private BaseActionHandler actionHandler;

    SkillType(int id, Class<? extends BaseActionHandler> actionHandlerClazz) {
        this.id = id;
        this.actionHandlerClazz = actionHandlerClazz;
    }

    public static SkillType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public int getId() {
        return id;
    }

    public Class<? extends IActionHandler> getActionHandlerClazz() {
        return actionHandlerClazz;
    }

    private BaseActionHandler getActionHandler() {
        if (actionHandler == null) {
            try {
                actionHandler = actionHandlerClazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return actionHandler;
    }

}
