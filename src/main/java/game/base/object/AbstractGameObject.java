package game.base.object;

/**
 * 游戏中的对象顶级抽象类
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:43
 */

public abstract class AbstractGameObject {

    /**
     * 全局唯一id
     */
    private long objectId;

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }
}
