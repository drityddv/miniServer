package game.user.pack.model.sort;

import java.util.Comparator;

import game.user.pack.model.PackSquare;

/**
 * configId比较器
 *
 * @author : ddv
 * @since : 2019/7/10 上午10:40
 */

public class ConfigIdComparator implements Comparator<PackSquare> {

    @Override
    public int compare(PackSquare o1, PackSquare o2) {
        if (o1.isEmpty()) {
            return 1;
        }
        if (o2.isEmpty()) {
            return -1;
        }

        if (o1.getItem().getConfigId() == o2.getItem().getConfigId()) {
            return 0;
        }
        return o1.getItem().getConfigId() < o2.getItem().getConfigId() ? -1 : 1;
    }

    @Override
    public Comparator<PackSquare> thenComparing(Comparator<? super PackSquare> other) {
        return new ItemNumComparator();
    }
}
