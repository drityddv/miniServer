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

    public static PackSquare valueOf(int index, AbstractItem item) {
        PackSquare packSquare = new PackSquare();
        packSquare.index = index;
        packSquare.item = item;
        return packSquare;
    }

    public void clear() {
        this.item = null;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public void add(int num) {
        item.add(num);
    }

    public void reduce(int num) {
        item.reduce(num);
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

    // 添加前调用者确认是否超过重叠上限
    public void addItem(AbstractItem item, int num) {
        this.item = item;
    }

    public void addUnOverLimitItem(AbstractItem item) {
        this.item = item;
    }

    public int getItemNum() {
        return item == null ? 0 : item.getNum();
    }

    @Override
    public String toString() {
        return "PackSquare{" + "index=" + index + ", item=" + item + '}'+'\n';
    }
}
