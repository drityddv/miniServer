package game.base.object;

/**
 * 生物单位抽象类
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:46
 */

public abstract class AbstractCreature extends AbstractGameObject {

    // 属性容器

    // 是否存活 1:存活
    private int isAlive = 1;

    public int getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }
}
