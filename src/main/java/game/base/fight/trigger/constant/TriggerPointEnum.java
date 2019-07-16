package game.base.fight.trigger.constant;

/**
 * @author : ddv
 * @since : 2019/7/15 2:19 PM
 */

public enum TriggerPointEnum {
    // 移动后
    AFTER_MOVE(true),;

    // 死亡是否任然可以触发
    private boolean deadStillTrigger;

    TriggerPointEnum(boolean deadTrigger) {
        this.deadStillTrigger = deadTrigger;
    }

    public boolean isDeadStillTrigger() {
        return deadStillTrigger;
    }
}
