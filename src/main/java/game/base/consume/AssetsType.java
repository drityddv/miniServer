package game.base.consume;

import java.util.HashMap;
import java.util.Map;

import game.base.message.I18N;
import game.role.player.model.Player;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/11 9:02 PM
 */

public enum AssetsType {
    /**
     * 道具
     */
    ITEM(1) {
        @Override
        public VerifyResult verify(Player player, ConsumeParam param) {
            if (!player.getPack().isEnoughItem(param.getConfigId(), param.getValue())) {
                return VerifyResult.failed(I18N.ITEM_NUM_NOT_ENOUGH);
            }
            return VerifyResult.success();
        }

        @Override
        public void consume(Player player, ConsumeParam consumeParam) {
            SpringContext.getPackService().reduceItem(player, consumeParam.getConfigId(), consumeParam.getValue());
        }
    },
    /**
     * 技能点
     */
    SKILL_POINT(2) {
        @Override
        public VerifyResult verify(Player player, ConsumeParam param) {
            if (player.getSkillPoint() <= param.getValue()) {
                return VerifyResult.failed(I18N.SKILL_POINT_NOT_ENOUGH);
            }
            return VerifyResult.success();
        }

        @Override
        public void consume(Player player, ConsumeParam consumeParam) {
            int skillPoint = player.getSkillPoint();
            player.setSkillPoint(skillPoint - consumeParam.getValue());
        }
    },;

    AssetsType(int id) {
        this.id = id;
    }

    public static Map<String, AssetsType> NAME_TO_TYPE = new HashMap<>(AssetsType.values().length);
    public static Map<Long, AssetsType> ID_TO_TYPE = new HashMap<>(AssetsType.values().length);

    static {
        for (AssetsType type : AssetsType.values()) {
            NAME_TO_TYPE.put(type.name(), type);
            ID_TO_TYPE.put(type.id, type);
        }
    }

    public static AssetsType getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public static AssetsType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    private long id;

    public long getId() {
        return id;
    }

    public VerifyResult verify(Player player, ConsumeParam param) {
        return VerifyResult.success();
    }

    public void consume(Player player, ConsumeParam consumeParam) {}
}
