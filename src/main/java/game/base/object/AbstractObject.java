package game.base.object;

/**
 * 游戏中的对象顶级抽象类
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:43
 */

public abstract class AbstractObject {

    /**
     * 全局唯一id
     */
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
