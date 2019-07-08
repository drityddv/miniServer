package game.user.pack.model;

import game.user.item.base.model.AbstractItem;
import spring.SpringContext;

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

    // 添加前调用者确认是否超过重叠上限
    public void addItem(AbstractItem item, int num) {
        if (this.item == null) {
            this.item = SpringContext.getCommonService().createItem(item.getConfigId(), 0);
        }
        this.item.setNum(num);
    }

    // 增加不可堆叠的道具 使用前自行check
    public void addUnOverLimitItem(AbstractItem item) {
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public void reduce(int num) {
        item.reduce(num);
        check();
    }

    private void check() {
        if (item.getNum() == 0) {
            item = null;
        }
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

    public int getItemNum() {
        return item == null ? 0 : item.getNum();
    }

    @Override
    public String toString() {
        return "PackSquare{" + "index=" + index + ", item=" + item + '}' + '\n';
    }
}
