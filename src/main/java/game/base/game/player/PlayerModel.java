package game.base.game.player;

/**
 * @author : ddv
 * @since : 2019/6/22 下午3:21
 */

public enum PlayerModel {
    /**
     * 玩家的自身基本属性
     */
    PLAYER_SELF(1, "玩家基本属性"),

    TEST_MODEL(100, "测试模块");

    PlayerModel(int id, String modelName) {
        this.id = id;
        this.modelName = modelName;
    }

    private int id;
    private String modelName;
}
