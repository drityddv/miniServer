package game.user.pack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.pack.constant.PackConstant;
import game.user.pack.model.sort.ConfigIdComparator;

/**
 * 背包
 *
 * @author : ddv
 * @since : 2019/5/31 下午5:37
 */

public class Pack {

    private long playerId;
    private int size;
    private List<PackSquare> packSquares;

    public static Pack valueOf(long playerId) {
        Pack pack = new Pack();
        pack.playerId = playerId;
        pack.size = PackConstant.PACK_MAX_SIZE;
        pack.packSquares = new ArrayList<>(PackConstant.PACK_MAX_SIZE);

        // 创建时先初始化每个格子
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
            availableSize = countAvailableSize(packSquare, item, availableSize);
        }

        return false;
    }

    public void sortPack() {
        Collections.sort(packSquares, new ConfigIdComparator());
        int index = 0;
        for (PackSquare packSquare : packSquares) {
            packSquare.setIndex(index++);
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
                    if (packSquare.isEmpty() || isEqualSquare(packSquare, item)) {
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
                    if (packSquare.isEmpty() || isEqualSquare(packSquare, item)) {
                        packSquare.addItem(item);
                    }
                }
            }

        }
    }

    // 直接消耗 不做容量检查
    public void doReduceItem(AbstractItem item) {
        int remainCount = item.getNum();
        for (PackSquare packSquare : packSquares) {
            if (remainCount == 0) {
                return;
            }
            if (isEqualSquare(packSquare, item)) {
                packSquare.reduce(item);
            }
        }
    }

    // 格子内的道具是否是同一种道具
    private boolean isEqualSquare(PackSquare packSquare, AbstractItem item) {
        return !packSquare.isEmpty() && packSquare.getItem().getConfigId() == item.getConfigId();
    }

    private int countAvailableSize(PackSquare packSquare, AbstractItem item, int availableSize) {
        switch (item.getOverLimit()) {
            case 0: {
                if (packSquare.isEmpty() || packSquare.getItem().getConfigId() == item.getConfigId()) {
                    return item.getNum();
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
                if (packSquare.isEmpty() || packSquare.getItem().getConfigId() == item.getConfigId()) {
                    availableSize += item.getOverLimit() - packSquare.getItemNum();
                }
            }
        }
        return availableSize;
    }

    // 获取背包中道具数量
    public int getItemNum(AbstractItem item) {
        int totalInPack = 0;
        for (PackSquare packSquare : packSquares) {
            if (isEqualSquare(packSquare, item)) {
                totalInPack += packSquare.getItemNum();
            }

        }
        return totalInPack;
    }

    // 判断背包中道具数量是否满足
    public boolean isEnoughItem(AbstractItem item) {
        int totalInPack = 0;
        for (PackSquare packSquare : packSquares) {
            if (isEqualSquare(packSquare, item)) {
                totalInPack += packSquare.getItemNum();
            }
            if (totalInPack >= item.getNum()) {
                return true;
            }
        }
        return false;
    }

    // 调用之前要检查数量是否足够
    public void reduceItem(AbstractItem item, int num) {
        int remainCount = num;
        List<PackSquare> squareList = getSquare(item);
        for (PackSquare square : squareList) {
            if (remainCount <= 0) {
                return;
            }
            int counts = square.getItemNum();
            int currentReduce = num - counts > 0 ? counts : num;
            square.reduce(currentReduce);
            remainCount -= currentReduce;
        }
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

    public AbstractItem getItem(AbstractItem item) {
        List<PackSquare> square = getSquare(item);
        if (CollectionUtils.isEmpty(square)) {
            return null;
        } else {
            return square.get(0).getItem();
        }
    }

    // get and set
    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<PackSquare> getPackSquares() {
        return packSquares;
    }

    @Override
    public String toString() {
        return "Pack{" + "playerId=" + playerId + ", size=" + size + ", packSquares=" + '\n' + packSquares + '}';
    }

}
