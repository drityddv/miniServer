package game.publicsystem.rank.packet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.publicsystem.rank.model.type.BaseRankInfo;

/**
 * @author : ddv
 * @since : 2019/8/5 6:03 PM
 */

public class SM_RankVo {
    private static final Logger logger = LoggerFactory.getLogger(SM_RankVo.class);
    private List<BaseRankInfo> rankInfoList = new ArrayList<>();

    public static SM_RankVo valueOf(List<BaseRankInfo> baseRankInfoList) {
        SM_RankVo sm = new SM_RankVo();
        sm.rankInfoList = baseRankInfoList;
        return sm;
    }

    @Action
    private void action() {
        logger.info("打印排行榜信息");
        int index = 1;
        for (BaseRankInfo rankInfo : rankInfoList) {
            logger.info("排名[{}] 玩家[{}] 战力数[{}]", index++, rankInfo.getId(), rankInfo.getValue());
        }
    }
}
