package game.publicsystem.rank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.MessageEnum;
import game.publicsystem.rank.constant.RankType;
import game.publicsystem.rank.entity.ServerRankEnt;
import game.publicsystem.rank.model.ServerRank;
import game.publicsystem.rank.model.rank.AbstractRank;
import game.publicsystem.rank.model.type.BaseRankInfo;
import game.publicsystem.rank.packet.SM_RankDetailVo;
import game.role.player.model.Player;
import net.utils.PacketUtil;
import quartz.constant.JobGroupEnum;
import quartz.job.common.rank.RankUpdateCacheJob;
import quartz.job.model.JobEntry;
import utils.snow.IdUtil;

/**
 * 本服排行榜接口
 *
 * @author : ddv
 * @since : 2019/8/5 4:40 PM
 */
@Component
public class RankService implements IRankService {

    private static final Logger logger = LoggerFactory.getLogger(RankService.class);

    @Autowired
    private RankManager rankManager;

    @Override
    public void init() {
        getServerRankInfo().init();
        JobEntry
            .newRateJob(RankUpdateCacheJob.class, 5000, 0, IdUtil.getLongId(), JobGroupEnum.RANK_UPDATE.name(), null)
            .schedule();
    }

    @Override
    public void addRankInfo(BaseRankInfo baseRankInfo) {
        logger.info("玩家[{}] 分数[{}] 尝试添加排行榜", baseRankInfo.getId(), baseRankInfo.getValue());
        ServerRank serverRankInfo = getServerRankInfo();
        serverRankInfo.addRankInfo(baseRankInfo);
    }

    @Override
    public void addRankInfoCallback(Player player, BaseRankInfo baseRankInfo) {
        logger.info("玩家[{}] 分数[{}] 尝试添加排行榜", baseRankInfo.getId(), baseRankInfo.getValue());
        ServerRank serverRankInfo = getServerRankInfo();
        serverRankInfo.addRankInfo(player, baseRankInfo);
    }

    @Override
    public ServerRank getServerRankInfo(Player player) {
        ServerRank serverRank = getServerRankInfo();
        serverRank.sendInfo(player);
        return serverRank;
    }

    private ServerRankEnt getServerRankEnt() {
        return rankManager.loadOrCreate();
    }

    private ServerRank getServerRankInfo() {
        return getServerRankEnt().getServerRank();
    }

    @Override
    public void saveRankInfo() {
        rankManager.save(getServerRankEnt());
    }

    @Override
    public void updateCache() {
        // logger.info("刷新排行榜缓存...");
        getServerRankInfo().updateCache();
    }

    @Override
    public BaseRankInfo getPlayerCacheRankInfo(RankType rankType, String id) {
        return getServerRankInfo().getPlayerCacheRankInfo(rankType, id);
    }

    @Override
    public void getRankInfo(Player player, RankType rankType, int start, int end) {
        if (start < 0 || end <= start || rankType == null) {
            PacketUtil.send(player, MessageEnum.RANK_INDEX_ERROR);
            return;
        }
        AbstractRank rank = getServerRankInfo().getRank(rankType);
        List<BaseRankInfo> vo = rank.getCacheVo();
        PacketUtil.send(player,
            SM_RankDetailVo.valueOf(start, vo.stream().skip(start).limit(end - start).collect(Collectors.toList())));

    }
}
