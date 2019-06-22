package game.base.map.base;

/**
 * @author : ddv
 * @since : 2019/6/20 下午10:10
 */

public enum CreatureType {
    /**
     * 玩家
     */
    TYPE_PLAYER(1, "玩家"),
    /**
     * npc
     */
    TYPE_NPC(2, "NPC"),
    /**
     * 怪物
     */
    TYPE_MONSTER(3, "怪物"),;

    int typeId;

    String typeName;

    CreatureType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

}
