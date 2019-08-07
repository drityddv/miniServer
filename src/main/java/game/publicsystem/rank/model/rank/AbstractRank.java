package game.publicsystem.rank.model.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import game.base.executor.constant.ExecutorConst;
import game.publicsystem.rank.constant.RankConst;
import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.DefaultComparator;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * 排行榜基类
 *
 * @author : ddv
 * @since : 2019/8/6 10:54 AM
 */

public abstract class AbstractRank {

    protected Object[] locks = new Object[ExecutorConst.POOL_SIZE];

    protected volatile List<BaseRankInfo> cache = new ArrayList<>();

    protected transient ConcurrentSkipListMap<BaseRankInfo, Object> rankDataMap =
        new ConcurrentSkipListMap<>(new DefaultComparator());

    public AbstractRank() {}

    /**
     * 获取rank的类型
     *
     * @return
     */
    public abstract RankType getRankType();

    public void addRankInfo(BaseRankInfo rankInfo) {
        synchronized (getLock(rankInfo)) {
            rankDataMap.put(rankInfo, rankInfo);
            if (rankDataMap.size() > RankConst.MAX_SIZE) {
                removeLast();
            }
        }
    }

    // 返回段位数据
    public void addRankInfoCallback(Player player, BaseRankInfo rankInfo) {
        synchronized (getLock(rankInfo)) {
            rankDataMap.put(rankInfo, rankInfo);
            if (rankDataMap.size() > RankConst.MAX_SIZE) {
                removeLast();
            }
            cache = new ArrayList<>(rankDataMap.keySet());
            PacketUtil.send(player, cache);
        }
    }

    private void removeLast() {
        rankDataMap.pollLastEntry();
    }

    public List<BaseRankInfo> getCache() {
        return cache;
    }

    // FIXME 偶现死循环
    public void init() {
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
        rankDataMap.clear();
        cache.forEach(baseRankInfo -> {
            baseRankInfo.init();
            rankDataMap.put(baseRankInfo, baseRankInfo);
        });
    }

    public void updateCache() {
        cache = new ArrayList<>(rankDataMap.keySet());
        while (rankDataMap.size() > RankConst.MAX_SIZE) {
            removeLast();
        }
    }

    public Object getLock(BaseRankInfo baseRankInfo) {
        int index = baseRankInfo.getId().hashCode() % locks.length;
        return locks[index];
    }
}
