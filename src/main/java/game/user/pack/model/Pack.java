package game.user.pack.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import game.common.I18N;
import game.common.exception.RequestException;
import game.user.item.base.model.AbstractItem;
import game.user.pack.constant.PackConstant;
import spring.SpringContext;

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

    public void addItems(AbstractItem item) {
        int num = item.getNum();
        int overLimit = item.getOverLimit();
        int remainCount = num;
        List<PackSquare> squareList = getSquare(item);
        List<PackSquare> emptySquares = getEmptySquares();

        // 可堆叠有数量限制
        if (overLimit > 1) {
            // 背包无此类装备且无空格栏
            if (!isInPack(item) && !isExistEmptySquare()) {
                RequestException.throwException(I18N.PACK_FULL);
            }

            if (!isEnoughSize(item)) {
                RequestException.throwException(I18N.PACK_SIZE_NOT_ENOUGH);
            }

            for (PackSquare square : squareList) {
                if (remainCount <= 0) {
                    return;
                }
                int counts = square.getItemNum();
                // 此次添加的实际数量
                int addCount = remainCount > (overLimit - counts) ? overLimit - counts : remainCount;
                square.addItem(item, addCount);
                remainCount -= addCount;
            }

            for (PackSquare square : emptySquares) {
                if (remainCount <= 0) {
                    return;
                }

                int addCount = remainCount > overLimit ? overLimit : remainCount;
                square.addItem(item, addCount);
                remainCount -= addCount;
            }

        } else if (overLimit == 1) {
            // 不可重叠道具添加

            if (emptySquares.size() < num) {
                RequestException.throwException(I18N.PACK_SIZE_NOT_ENOUGH);
            }

            for (PackSquare emptySquare : emptySquares) {
                emptySquare.addUnOverLimitItem(SpringContext.getCommonService().createItem(item.getConfigId(), 1));
                num--;
                if (num <= 0) {
                    break;
                }
            }

        } else {
            // 重叠无限制道具
            if (!isEnoughSize(item)) {
                RequestException.throwException(I18N.PACK_SIZE_NOT_ENOUGH);
            }
            if (squareList.size() != 0) {
                squareList.get(0).addItem(item, num);
            } else {
                emptySquares.get(0).addItem(item, num);
            }

        }
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

    // 这里的item都是可堆叠的
    public boolean isEnoughSize(AbstractItem item) {
        List<PackSquare> squareList = getSquare(item);
        List<PackSquare> emptySquares = getEmptySquares();
        int overLimit = item.getOverLimit();
        int count = item.getNum();
        int emptyCount = 0;

        // 先看是不是无限堆叠的
        if (overLimit == 0) {
            if (squareList.size() > 0) {
                return true;
            }
            return emptySquares.size() != 0;
        }

        // 先看此类道具的已有格子容量是否足够
        for (PackSquare packSquare : squareList) {
            int counts = packSquare.getItemNum();
            emptyCount += overLimit - counts;
        }

        // 再看空道具栏是否足够
        if (emptyCount < count) {
            emptyCount += emptySquares.size() * overLimit;
        }

        return emptyCount >= count;
    }

    private boolean isExistEmptySquare() {
        return getEmptySquare() != null;
    }

    private boolean isInPack(AbstractItem item) {
        return getSquare(item).size() != 0;
    }

    // 调用者需要自行check
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

    private PackSquare getEmptySquare() {
        // 如果都满了 那就返回null
        for (PackSquare packSquare : packSquares) {
            if (packSquare.isEmpty()) {
                return packSquare;
            }
        }
        return null;
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

    public void setPackSquares(List<PackSquare> packSquares) {
        this.packSquares = packSquares;
    }

    @Override
    public String toString() {
        return "Pack{" + "playerId=" + playerId + ", size=" + size + ", packSquares=" + '\n' + packSquares + '}';
    }

}
