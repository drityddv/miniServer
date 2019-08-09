package game.publicsystem.rank.packet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.anno.Action;
import game.publicsystem.rank.model.type.BaseRankInfo;

/**
 * @author : ddv
 * @since : 2019/8/9 11:51 AM
 */

public class SM_RankDetailVo {
    private static final Logger logger = LoggerFactory.getLogger(SM_RankDetailVo.class);
    private int start;
    private List<BaseRankInfo> rankInfoList;

    public static SM_RankDetailVo valueOf(int start, List<BaseRankInfo> baseRankInfoList) {
        SM_RankDetailVo sm = new SM_RankDetailVo();
        sm.start = start;
        sm.rankInfoList = baseRankInfoList;
        return sm;
    }

    @Action
    private void action() {
        for (BaseRankInfo rankInfo : rankInfoList) {
            logger.info("排名[{}] 玩家[{}] 分数[{}]", ++start, rankInfo.getId(), rankInfo.getValue());
        }
    }
}
