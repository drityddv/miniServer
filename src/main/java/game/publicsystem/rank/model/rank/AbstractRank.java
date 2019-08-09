package game.publicsystem.rank.model.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    // 玩家查看的缓存
    protected volatile List<BaseRankInfo> cache = new ArrayList<>();
    // 玩家修改的缓存
    protected transient Map<String, BaseRankInfo> playerCache = new ConcurrentHashMap<>();

    protected transient ConcurrentSkipListMap<BaseRankInfo, String> rankDataMap =
        new ConcurrentSkipListMap<>(new DefaultComparator());

    public AbstractRank() {}

    /**
     * 获取rank的类型
     *
     * @return
     */
    public abstract RankType getRankType();

    public void addRankInfo(BaseRankInfo rankInfo) {
        BaseRankInfo cacheRank = playerCache.get(rankInfo.getId());
        if (cacheRank == null) {
            playerCache.put(rankInfo.getId(), rankInfo);
            synchronized (getLock(rankInfo)) {
                rankDataMap.put(rankInfo, rankInfo.getId());
                if (rankDataMap.size() > RankConst.MAX_SIZE) {
                    removeLast();
                }
            }

        } else {
            if (cacheRank.getValue() == rankInfo.getValue()) {
                return;
            }
            synchronized (getLock(rankInfo)) {
                rankDataMap.remove(cacheRank);
                playerCache.put(rankInfo.getId(), rankInfo);
                rankDataMap.put(rankInfo, rankInfo.getId());
                if (rankDataMap.size() > RankConst.MAX_SIZE) {
                    removeLast();
                }
            }
        }

    }

    // 返回段位数据
    public void addRankInfoCallback(Player player, BaseRankInfo rankInfo) {
        BaseRankInfo cacheRank = playerCache.get(rankInfo.getId());
        if (cacheRank == null) {
            playerCache.put(rankInfo.getId(), rankInfo);
            synchronized (getLock(rankInfo)) {
                rankDataMap.put(rankInfo, rankInfo.getId());
                if (rankDataMap.size() > RankConst.MAX_SIZE) {
                    removeLast();
                }
                List<BaseRankInfo> tempCache = new ArrayList<>(rankDataMap.keySet());
                cache = tempCache;
                PacketUtil.send(player, cache);
            }

        } else {
            if (cacheRank.getValue() == rankInfo.getValue()) {
                return;
            }
            synchronized (getLock(rankInfo)) {
                rankDataMap.remove(cacheRank);
                playerCache.put(rankInfo.getId(), rankInfo);
                rankDataMap.put(rankInfo, rankInfo.getId());
                if (rankDataMap.size() > RankConst.MAX_SIZE) {
                    removeLast();
                }
                cache = new ArrayList<>(rankDataMap.keySet());
                PacketUtil.send(player, cache);
            }
        }

    }

    private void removeLast() {
        rankDataMap.pollLastEntry();
    }

    public List<BaseRankInfo> getCache() {
        return cache;
    }

    public void init() {
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }

        cache.forEach(baseRankInfo -> {
            baseRankInfo.init();
            playerCache.put(baseRankInfo.getId(), baseRankInfo);
            rankDataMap.put(baseRankInfo, baseRankInfo.getId());
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
