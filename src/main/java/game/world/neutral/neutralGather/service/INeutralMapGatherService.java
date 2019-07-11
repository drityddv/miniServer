package game.world.neutral.neutralGather.service;

import game.world.neutral.neutralMap.model.NeutralMapInfo;

/**
 * @author : ddv
 * @since : 2019/7/5 下午2:37
 */

public interface INeutralMapGatherService {

    /**
     * 初始化npc
     *
     * @param mapCommonInfo
     */
    void initNpcResource(NeutralMapInfo mapCommonInfo);

}
