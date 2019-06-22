package game.base.game.attribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.base.game.player.PlayerModel;

/**
 * @author : ddv
 * @since : 2019/6/22 下午12:12
 */

public class AttributeSquare {

    private AttributeType type;
    // 玩家模块提供的属性值
    private Map<PlayerModel, Long> modelValue;
    // 属性模块的总属性值
    private long value;

    public static AttributeSquare valueOf(AttributeType type) {
        AttributeSquare square = new AttributeSquare();
        PlayerModel[] playerModels = PlayerModel.values();

        square.modelValue = new ConcurrentHashMap<>(playerModels.length);
		long tempValue = 1;
        // TODO 属性数值需要从service加载
        for (PlayerModel model : playerModels) {
            square.modelValue.put(model, 0L);
        }

        square.type = type;
        square.value = 0L;
        return square;
    }

    // get and set
    public AttributeType getType() {
        return type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AttributeSquare{" + "type=" + type + ", value=" + value + '}';
    }
}
