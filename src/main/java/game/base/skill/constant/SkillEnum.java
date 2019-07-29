package game.base.skill.constant;

import java.util.HashMap;
import java.util.Map;

import game.base.fight.model.skill.action.handler.BaseActionHandler;
import game.base.fight.model.skill.action.handler.IActionHandler;
import game.base.fight.model.skill.action.handler.impl.Avatar;
import game.base.fight.model.skill.action.handler.impl.FlameStorm;
import game.base.fight.model.skill.action.handler.impl.FrostBolt;
import game.base.fight.model.skill.action.handler.impl.HighNoonShoot;

/**
 * @author : ddv
 * @since : 2019/7/15 8:35 PM
 */

public enum SkillEnum {
    /**
     * 寒冰箭
     */
    Frost_Bolt(1, FrostBolt.class),
    /**
     * 午时已到
     */
    HighNoon_Shoot(2, HighNoonShoot.class),
    /**
     * 烈焰风暴
     */
    Flame_Storm(3, FlameStorm.class) {

    },

    Avatar_(4, Avatar.class);

    public static Map<SkillEnum, BaseActionHandler> TYPE_TO_HANDLER = new HashMap<>(SkillEnum.values().length);
    public static Map<Integer, SkillEnum> ID_TO_TYPE = new HashMap<>(SkillEnum.values().length);

    static {
        for (SkillEnum type : SkillEnum.values()) {
            TYPE_TO_HANDLER.put(type, type.getActionHandler());
            ID_TO_TYPE.put(type.id, type);
        }
    }

    private int id;
    // 技能动作处理器
    private Class<? extends BaseActionHandler> actionHandlerClazz;
    private BaseActionHandler actionHandler;

    SkillEnum(int id, Class<? extends BaseActionHandler> actionHandlerClazz) {
        this.id = id;
        this.actionHandlerClazz = actionHandlerClazz;
    }

    public static SkillEnum getById(int id) {
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
