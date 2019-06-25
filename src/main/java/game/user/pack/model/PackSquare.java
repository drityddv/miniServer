package game.user.pack.model;

import game.center.item.resource.Item;

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
    private Item item;

    // 数量
    private int counts;

    public static PackSquare valueOf(int index, Item item, int counts) {
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
        return counts == 0;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "PackSquare{" + "index=" + index + ", item=" + item + ", counts=" + counts + '}';
    }

}
