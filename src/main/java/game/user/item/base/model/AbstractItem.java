package game.user.item.base.model;

import game.base.object.AbstractGameObject;
import game.user.item.resource.ItemResource;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/6/25 下午4:25
 */

public abstract class AbstractItem extends AbstractGameObject implements Comparable<AbstractItem>, Cloneable {

    protected long configId;

    protected int num;

    public int getOverLimit() {
        return getResource().getOverLimit();
    }

    public boolean canOverLimit() {
        return getOverLimit() != 1;
    }

    public ItemResource getResource() {
        return SpringContext.getPackService().getResource(configId);
    }

    public void init(ItemResource itemResource) {
        configId = itemResource.getConfigId();
    }

    @Override
    public int compareTo(AbstractItem abstractItem) {
        return 0;
    }

    public long getConfigId() {
        return configId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void reduce(int count) {
        this.num -= count;
    }

    public void add(int count) {
        this.num += count;
    }
}
