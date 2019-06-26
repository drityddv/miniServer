package game.user.pack.model;

import game.user.item.base.model.AbstractItem;

/**
 * 背包格子
 *
 * @author : ddv
 * @since : 2019/6/8 上午12:44
 */

public class PackSquare {

    // 格子序号
    private int index;

    // 物品
    private AbstractItem item;

    // 数量
    private int counts;

    public static PackSquare valueOf(int index, AbstractItem item, int counts) {
        PackSquare packSquare = new PackSquare();
        packSquare.index = index;
        packSquare.item = item;
        packSquare.counts = counts;
        return packSquare;
    }

    public void clear() {
        this.item = null;
        this.counts = 0;
    }

    public boolean isEmpty() {
        return counts == 0 && item == null;
    }

    public void addCounts(int count) {
        this.counts += count;
    }

    public void reduceCounts(int count) {
        this.counts -= count;
    }

    // get and set
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AbstractItem getItem() {
        return item;
    }

    public void setItem(AbstractItem item) {
        this.item = item;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    // 添加前调用者确认是否超过重叠上限
    public void addItem(AbstractItem item, int num) {
        this.item = item;
        this.counts += num;
    }

    public void addUnOverLimitItem(AbstractItem item) {
        this.item = item;
        this.counts = 1;
    }

    @Override
    public String toString() {
        return "PackSquare{" + "index=" + index + ", item=" + item + ", counts=" + counts + '}';
    }
}
