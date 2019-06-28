package game.base.object;

import game.base.game.attribute.CreatureAttributeContainer;

/**
 * 生物单位抽象类
 *
 * @author : ddv
 * @since : 2019/5/6 下午8:46
 */

public abstract class AbstractCreature<T extends AbstractCreature> extends AbstractGameObject {

    // 属性容器
    public CreatureAttributeContainer<T> attributeContainer;

    // buff容器
    public AbstractCreature() {

    }

    public AbstractCreature(long objectId) {
        setObjectId(objectId);
    }

    public CreatureAttributeContainer<? extends AbstractCreature> getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(CreatureAttributeContainer<T> attributeContainer) {
        this.attributeContainer = attributeContainer;
    }

}
