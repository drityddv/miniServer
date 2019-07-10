package game.user.pack.model.sort;

import java.util.Comparator;

import game.user.pack.model.PackSquare;

/**
 * 相同道具 数量多的优先排在前面
 *
 * @author : ddv
 * @since : 2019/7/10 上午10:44
 */

public class ItemNumComparator implements Comparator<PackSquare> {

    @Override
    public int compare(PackSquare o1, PackSquare o2) {
        return o1.getItemNum() >= o2.getItemNum() ? -1 : 1;
    }
}
