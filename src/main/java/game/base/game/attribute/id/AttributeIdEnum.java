package game.base.game.attribute.id;

/**
 * @author : ddv
 * @since : 2019/6/27 上午9:59
 */

public enum AttributeIdEnum implements AttributeId {
    /**
     * 基础模块属性
     */
    BASE {
        @Override
        public String getName() {
            return "BASE";
        }
    },

    ;

    @Override
    public String getName() {
        return null;
    }
}
