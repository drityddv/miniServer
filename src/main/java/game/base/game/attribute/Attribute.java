package game.base.game.attribute;

import java.util.Map;

import game.base.game.player.PlayerModel;

/**
 * @author : ddv
 * @since : 2019/6/25 上午9:50
 */

public class Attribute {

    private PlayerModel model;

    private Map<AttributeType, Long> modelValue;

    public PlayerModel getModel() {
        return model;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public Map<AttributeType, Long> getModelValue() {
        return modelValue;
    }

    public void setModelValue(Map<AttributeType, Long> modelValue) {
        this.modelValue = modelValue;
    }
}
