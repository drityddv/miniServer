package game.publicsystem.rank.model;

import java.util.HashMap;
import java.util.Map;

import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.model.rank.AbstractRank;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.packet.SM_RankVo;
import game.role.player.model.Player;
import net.utils.PacketUtil;

/**
 * 服务器排行榜
 *
 * @author : ddv
 * @since : 2019/8/5 4:43 PM
 */

public class ServerRank {

    private Map<RankType, AbstractRank> abstractRankMap = new HashMap<>();

    public ServerRank() {}

    public static ServerRank valueOf() {
        ServerRank rankInfo = new ServerRank();
        for (RankType rankType : RankType.values()) {
            rankInfo.abstractRankMap.put(rankType, rankType.createRank());
        }
        return rankInfo;
    }

    // 只加数据 不刷新
    public void addRankInfo(BaseRankInfo baseRankInfo) {
        abstractRankMap.get(baseRankInfo.getType()).addRankInfo(baseRankInfo, false, null);
    }

    // 刷新vo缓存 下发排行榜vo信息
    public void addRankInfo(Player player, BaseRankInfo rankInfo) {
        abstractRankMap.get(rankInfo.getType()).addRankInfo(rankInfo, true, player);
    }

    public void sendInfo(Player player) {
        abstractRankMap.values().forEach(abstractRank -> {
            PacketUtil.send(player, SM_RankVo.valueOf(abstractRank.getCacheVo()));
        });
    }

    public void init() {
        abstractRankMap.values().forEach(AbstractRank::init);
    }

    public void updateCache() {
        abstractRankMap.values().forEach(AbstractRank::updateCache);
    }

    public BaseRankInfo getPlayerCacheRankInfo(RankType rankType, String id) {
        return abstractRankMap.get(rankType).getCacheRankInfo(id);
    }

    public AbstractRank getRank(RankType rankType) {
        return abstractRankMap.get(rankType);
    }
}
