package game.user.pack.model;

import java.util.List;
import java.util.Vector;

import game.base.item.Item;
import game.user.pack.constant.PackConstant;

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
        pack.packSquares = new Vector<>(PackConstant.PACK_MAX_SIZE);

        // 创建时先初始化每个格子
        for (int i = 0; i < pack.size; i++) {
            pack.packSquares.add(PackSquare.valueOf(i, null, 0));
        }

        return pack;
    }

    public void addItem(Item item, int count) {
        long itemId = item.getId();

        for (PackSquare packSquare : packSquares) {
            if (packSquare.getItem().getId() == itemId) {
                packSquare.addCounts(count);
                return;
            }
        }

        PackSquare emptySquare = getEmptySquare();
        emptySquare.setItem(item);
        emptySquare.setCounts(count);

    }

    public void useItem(Item item, int count) {
        PackSquare itemSquare = getItemSquare(item);

        if (itemSquare == null || itemSquare.getCounts() < count) {
            return;
        }

        itemSquare.reduceCounts(count);
    }

    private PackSquare getItemSquare(Item item) {
        long itemId = item.getId();

        for (PackSquare packSquare : packSquares) {
            if (packSquare.getItem().getId() == itemId) {
                return packSquare;
            }
        }
        return null;
    }

    private PackSquare getEmptySquare() {
        // 如果都满了 那就返回null
        for (PackSquare packSquare : packSquares) {
            if (packSquare.getCounts() == 0) {
                return packSquare;
            }
        }
        return null;
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
        return "Pack{" + "playerId=" + playerId + ", size=" + size + ", packSquares=" + packSquares + '}';
    }

}
