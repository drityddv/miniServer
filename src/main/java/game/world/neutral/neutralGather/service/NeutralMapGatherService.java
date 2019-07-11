package game.world.neutral.neutralGather.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.map.visible.impl.NpcVisibleInfo;
import game.scene.npc.reource.NpcResource;
import game.world.neutral.neutralMap.model.NeutralMapInfo;
import game.world.neutral.neutralMap.model.NeutralMapScene;
import spring.SpringContext;

/**
 * @author : ddv
 * @since : 2019/7/5 下午2:39
 */

@Component
public class NeutralMapGatherService implements INeutralMapGatherService {

    private static final Logger logger = LoggerFactory.getLogger(NeutralMapGatherService.class);

    @Override
    public void initNpcResource(NeutralMapInfo mapCommonInfo) {
        List<NpcVisibleInfo> npcVisibleInfoList = new ArrayList<>();
        NeutralMapScene mapScene = mapCommonInfo.getMapScene();
        List<NpcResource> npcResources = SpringContext.getNpcManager().getNpcByMapId(mapCommonInfo.getMapId());
        for (NpcResource npcResource : npcResources) {
            NpcVisibleInfo npcVisibleInfo = NpcVisibleInfo.valueOf(npcResource);
            npcVisibleInfoList.add(npcVisibleInfo);
            mapScene.getNpcMap().put(npcVisibleInfo.getId(), npcVisibleInfo);
        }

    }
}
