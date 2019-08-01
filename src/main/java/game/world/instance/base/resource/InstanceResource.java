package game.world.instance.base.resource;

import java.util.HashMap;
import java.util.Map;

import resource.anno.Init;
import resource.anno.MiniResource;
import resource.constant.CsvSymbol;

/**
 * @author : ddv
 * @since : 2019/7/31 11:32 AM
 */

@MiniResource
public class InstanceResource {

    private long configId;
    private int mapId;
    private int totalStage;
    // 副本最大通关时间
    private long maxTime;
    // stage -- hatchConfigId
    private Map<Integer, Long> stageHatchResourceMap;
    // 1:1,2:2,3:3
    private String hatchConfigString;
    private long dropConfigId;
    // 切阶段延迟 次数默认阶段数 -1
    private long changeStageDelay;

    @Init
    private void init() {
        stageHatchResourceMap = new HashMap<>();
        String[] split = hatchConfigString.split(CsvSymbol.Comma);
        for (String s : split) {
            String[] split1 = s.split(CsvSymbol.Colon);
            stageHatchResourceMap.put(Integer.parseInt(split1[0]), Long.parseLong(split1[1]));
        }
    }

    public long getConfigId() {
        return configId;
    }

    public int getMapId() {
        return mapId;
    }

    public int getTotalStage() {
        return totalStage;
    }

    public Map<Integer, Long> getStageHatchResourceMap() {
        return stageHatchResourceMap;
    }

    public long getChangeStageDelay() {
        return changeStageDelay;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public long getDropConfigId() {
        return dropConfigId;
    }
}
