package game.publicsystem.rank.model.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import game.publicsystem.rank.constant.RankConst;
import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.DefaultComparator;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.role.player.model.Player;
import net.utils.PacketUtil;
import spring.SpringContext;

/**
 * 排行榜基类
 *
 * @author : ddv
 * @since : 2019/8/6 10:54 AM
 */

public abstract class AbstractRank {
    // lock
    private final Object lock = new Object();
    // 玩家查看的缓存 100
    protected transient volatile List<BaseRankInfo> cacheVo = new ArrayList<>();
    // 玩家修改的缓存
    protected Map<String, BaseRankInfo> playerCache = new ConcurrentHashMap<>();
	//
    protected transient ConcurrentSkipListMap<BaseRankInfo, String> rankDataMap =
        new ConcurrentSkipListMap<>(new DefaultComparator());

    public AbstractRank() {}

    /**
     * 获取rank的类型
     *
     * @return
     */
    public abstract RankType getRankType();

    public void addRankInfo(BaseRankInfo rankInfo, boolean refreshCache, Player player) {
        synchronized (lock) {
            BaseRankInfo cacheRank = playerCache.get(rankInfo.getId());
            if (cacheRank == null) {
                playerCache.put(rankInfo.getId(), rankInfo);
                rankDataMap.put(rankInfo, rankInfo.getId());

            } else {
                if (cacheRank.getValue() == rankInfo.getValue()) {
                    return;
                }
                rankDataMap.remove(cacheRank);
                playerCache.put(rankInfo.getId(), rankInfo);
                rankDataMap.put(rankInfo, rankInfo.getId());
            }

            // 检查数量
            if (rankDataMap.size() > RankConst.MAX_SIZE) {
                removeLast();
            }
            // 刷新缓存
            if (refreshCache) {
                updateCache();
                PacketUtil.send(player, cacheVo);
                SpringContext.getRankService().saveRankInfo();
            }
        }
    }

    private void removeLast() {
        Map.Entry<BaseRankInfo, String> entry = rankDataMap.pollLastEntry();
        playerCache.remove(entry.getValue());
    }

    public List<BaseRankInfo> getCacheVo() {
        return cacheVo;
    }

    public void init() {
        playerCache.values().forEach(rankInfo -> {
            rankDataMap.put(rankInfo, rankInfo.getId());
        });
        cacheVo = new ArrayList<>(rankDataMap.keySet());
    }

    public void updateCache() {
        cacheVo = new ArrayList<>(rankDataMap.keySet());
        SpringContext.getRankService().saveRankInfo();
    }

    public BaseRankInfo getCacheRankInfo(String id) {
        return playerCache.get(id);
    }

}
