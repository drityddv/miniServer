package game.base.fight.base.model;

import game.base.effect.model.constant.RestrictStatusEnum;

/**
 * @author : ddv
 * @since : 2019/7/29 3:06 PM
 */

public class ActionResult {
    private long id;

    private long value;

    private RestrictStatusEnum restrictStatus;

    public static ActionResult valueOf() {
        ActionResult result = new ActionResult();
        return result;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public RestrictStatusEnum getRestrictStatus() {
        return restrictStatus;
    }

    public void setRestrictStatus(RestrictStatusEnum restrictStatus) {
        this.restrictStatus = restrictStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
