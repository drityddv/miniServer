package game.publicsystem.rank.model;

import java.util.Comparator;

import game.publicsystem.rank.model.type.BaseRankInfo;

/**
 * 按照value从高到低排序
 *
 * @author : ddv
 * @since : 2019/8/5 5:54 PM
 */

public class DefaultComparator implements Comparator<BaseRankInfo> {

    @Override
    public int compare(BaseRankInfo o1, BaseRankInfo o2) {
        long first = o1.getValue();
        long second = o2.getValue();

        if (first == second) {
            return 1;
        }

        if (first > second) {
            return -1;
        }

        return 1;
    }
}
