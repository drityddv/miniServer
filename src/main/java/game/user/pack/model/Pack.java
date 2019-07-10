package game.user.pack.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import game.user.pack.constant.PackConstant;
import game.user.pack.model.sort.ConfigIdComparator;
import spring.SpringContext;

/**
 * 背包
 *
 * @author : ddv
 * @since : 2019/5/31 下午5:37
 */

public class Pack {

    private int size;
    private List<PackSquare> packSquares;

    public static Pack valueOf() {
        Pack pack = new Pack();
        pack.size = PackConstant.PACK_MAX_SIZE;
        pack.packSquares = new ArrayList<>(PackConstant.PACK_MAX_SIZE);
        for (int i = 0; i < pack.size; i++) {
            pack.packSquares.add(PackSquare.valueOf(i, null));
        }
        return pack;
    }

    public boolean addItem(AbstractItem item) {
        if (!isEnoughSize(item)) {
            return false;
        }
        doAddItem(item);
        return true;
    }

    public void addItemWithThrow(AbstractItem item) {
        if (!isEnoughSize(item)) {
            RequestException.throwException(I18N.PACK_SIZE_NOT_ENOUGH);
        }
        doAddItem(item);
    }

    // 统计物品的数量
    public int countItemNum(AbstractItem item) {
        int resultNum = 0;
        long configId = item.getConfigId();
        for (PackSquare square : packSquares) {
            AbstractItem squareItem = square.getItem();
            if (squareItem != null && squareItem.getConfigId() == configId) {
                resultNum += squareItem.getNum();
            }
        }
        return resultNum;
    }

    // 检查背包是否可以塞得下某个道具
    public boolean isEnoughSize(AbstractItem item) {
        // 检查过程背包可以塞得下的数量
        int availableSize = 0;
        for (PackSquare packSquare : packSquares) {
            if (availableSize >= item.getNum()) {
                return true;
            }
            availableSize = countAvailableSize(packSquare, item.getConfigId(), item.getNum(), availableSize);
        }
        return false;
    }

    // 检查背包是否可以塞得下某个道具
    public boolean isEnoughSize(long itemConfigId, int itemNum) {
        int availableSize = 0;
        for (PackSquare packSquare : packSquares) {
            if (availableSize >= itemNum) {
                return true;
            }
            availableSize = countAvailableSize(packSquare, itemConfigId, itemNum, availableSize);
        }
        return false;
    }

    public void sortPack() {
        packSquares.sort(new ConfigIdComparator());
        int index = 0;
        for (PackSquare packSquare : packSquares) {
            packSquare.setIndex(index++);
        }
    }

    // 直接消耗 不做容量检查
    public void doReduceItem(AbstractItem item) {
        int remainCount = item.getNum();
        for (PackSquare packSquare : packSquares) {
            if (remainCount == 0) {
                return;
            }
            if (isSquareItemEqual(packSquare, item.getConfigId())) {
                remainCount = packSquare.reduce(item.getConfigId(), item.getNum());
            }
        }
    }

    public void doReduceItem(long configId, int num) {
        int remainCount = num;
        for (PackSquare packSquare : packSquares) {
            if (remainCount == 0) {
                return;
            }
            if (isSquareItemEqual(packSquare, configId)) {
                remainCount = packSquare.reduce(configId, num);
            }
        }
    }

    // 获取背包中道具数量
    public int getItemNum(AbstractItem item) {
        int totalInPack = 0;
        for (PackSquare packSquare : packSquares) {
            if (isSquareItemEqual(packSquare, item.getConfigId())) {
                totalInPack += packSquare.getItemNum();
            }

        }
        return totalInPack;
    }

    // 判断背包中道具数量是否满足
    public boolean isEnoughItem(AbstractItem item) {
        return isEnoughItem(item.getConfigId(), item.getNum());
    }

    public boolean isEnoughItem(long configId, int num) {
        int totalInPack = 0;
        for (PackSquare packSquare : packSquares) {
            if (isSquareItemEqual(packSquare, configId)) {
                totalInPack += packSquare.getItemNum();
            }
            if (totalInPack >= num) {
                return true;
            }
        }
        return false;
    }

    public List<PackSquare> getEmptySquares() {
        List<PackSquare> squareList = new ArrayList<>();
        for (PackSquare packSquare : packSquares) {
            if (packSquare.isEmpty()) {
                squareList.add(packSquare);
            }
        }
        return squareList;
    }

    public AbstractItem getItem(AbstractItem item) {
        List<PackSquare> square = getSquare(item);
        if (CollectionUtils.isEmpty(square)) {
            return null;
        } else {
            return square.get(0).getItem();
        }
    }

    // 直接插入 不做容量检查
    private void doAddItem(AbstractItem item) {
        for (PackSquare packSquare : packSquares) {
            if (item.getNum() == 0) {
                return;
            }
            switch (item.getOverLimit()) {
                case PackConstant.LIMIT_MAX: {
                    if (isSquareEmptyOrItemEqual(packSquare, item.getConfigId())) {
                        packSquare.addItem(item);
                    }
                    break;
                }
                case PackConstant.LIMIT_ONE: {
                    if (packSquare.isEmpty()) {
                        packSquare.addItem(item);
                    }
                    break;
                }
                default: {
                    if (isSquareEmptyOrItemEqual(packSquare, item.getConfigId())) {
                        packSquare.addItem(item);
                    }
                }
            }

        }
    }

    // 统计格子能塞下参数道具的个数
    private int countAvailableSize(PackSquare packSquare, long itemConfigId, int itemNum, int availableSize) {
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemConfigId);
        switch (itemResource.getOverLimit()) {
            case 0: {
                if (isSquareEmptyOrItemEqual(packSquare, itemConfigId)) {
                    return itemNum;
                }
                break;
            }
            case 1: {
                if (packSquare.isEmpty()) {
                    availableSize++;
                }
                break;
            }
            default: {
                if (isSquareEmptyOrItemEqual(packSquare, itemConfigId)) {
                    availableSize += itemResource.getOverLimit() - packSquare.getItemNum();
                }
            }
        }
        return availableSize;
    }

    // 格子内的道具是否是同一种道具
    private boolean isSquareItemEqual(PackSquare packSquare, long configId) {
        return !packSquare.isEmpty() && packSquare.getItem().getConfigId() == configId;
    }

    // 空格子或者道具相同
    private boolean isSquareEmptyOrItemEqual(PackSquare packSquare, long configId) {
        return packSquare.isEmpty() || packSquare.getItem().getConfigId() == configId;
    }

    private List<PackSquare> getSquare(AbstractItem item) {
        List<PackSquare> squareList = new ArrayList<>();
        long configId = item.getConfigId();

        for (PackSquare packSquare : packSquares) {
            AbstractItem packSquareItem = packSquare.getItem();
            if (packSquareItem != null && packSquareItem.getConfigId() == configId) {
                squareList.add(packSquare);
            }
        }
        return squareList;
    }

    public int getSize() {
        return size;
    }

    public List<PackSquare> getPackSquares() {
        return packSquares;
    }

}
