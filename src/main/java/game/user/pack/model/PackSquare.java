package game.user.pack.model;

import game.user.item.base.model.AbstractItem;
import game.user.pack.constant.PackConstant;
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

    public void addItem(AbstractItem item) {
        if (this.item == null) {
            this.item = SpringContext.getCommonService().createItem(item.getConfigId(), 0);
        }
        switch (item.getOverLimit()) {
            case PackConstant.LIMIT_MAX: {
                this.item.add(item.getNum());
                item.setNum(0);
                break;
            }
            case PackConstant.LIMIT_ONE: {
                if (getItemNum() == 1) {
                    return;
                }
                this.item.setNum(1);
                item.reduce(1);
                break;
            }
            default: {
                // 格子目前有多少数量
                int originCount = getItemNum();
                // 格子最大还可以塞多少数量
                int canAddNum = item.getOverLimit() - originCount;
                // 实际塞了多少
                int addCount = canAddNum >= item.getNum() ? item.getNum() : item.getNum() - canAddNum;
                this.item.add(addCount);
                item.reduce(addCount);
            }
        }
    }

    public boolean isEmpty() {
        return item == null;
    }

    // 返回剩余的待消耗量
    public int reduce(long configId, int num) {
        // 格子道具数量
        int originNum = getItemNum();
        // 实际减少的数量
        int reduceNum = originNum >= num ? num : originNum;
        item.reduce(reduceNum);
        check();
        return num - reduceNum;
    }

    private void check() {
        if (item.getNum() == 0) {
            item = null;
        }
    }

    public int getItemNum() {
        return item == null ? 0 : item.getNum();
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

    @Override
    public String toString() {
        return "PackSquare{" + "index=" + index + ", item=" + item + '}' + '\n';
    }

}
