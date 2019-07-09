package game.user.pack.packet;

import java.util.ArrayList;
import java.util.List;

import game.user.pack.model.Pack;
import game.user.pack.model.PackSquare;

/**
 * id: 91
 *
 * @author : ddv
 * @since : 2019/6/6 下午11:45
 */

public class SM_PackInfo {

    private int size;
    private List<PackSquare> squareList = new ArrayList<>();

    public static SM_PackInfo valueOf(Pack pack) {
        SM_PackInfo packInfo = new SM_PackInfo();
        packInfo.size = pack.getSize();
        for (PackSquare packSquare : pack.getPackSquares()) {
            if (!packSquare.isEmpty()) {
                packInfo.squareList.add(packSquare);
            }
        }
        return packInfo;
    }

    @Override
    public String toString() {
        return "SM_PackInfo{" + "size=" + size + ", squareList=" + squareList + '}';
    }
}
