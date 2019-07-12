package game.base.skill.resource;

import java.util.ArrayList;
import java.util.List;

import game.base.consume.AssetsConsume;
import game.base.consume.IConsume;
import resource.anno.Init;
import resource.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/7/12 11:33 AM
 */
@MiniResource
public class SkillLevelResource {
    /**
     * 配置表id
     */
    private long configId;
    /**
     * 技能id
     */
    private long skillId;
    private int level;
    /**
     * 下一等级配置表id
     */
    private int nextLevelConfigId;
    private List<IConsume> consumes;
    private String consumeString;

    @Init
    public void init() {
        analysisConsume();
    }

    private void analysisConsume() {
        consumes = new ArrayList<>();
        String[] split = consumeString.split(",");
        for (String value : split) {
            consumes.add(AssetsConsume.valueOf(value));
        }
    }

    public long getConfigId() {
        return configId;
    }

    public long getSkillId() {
        return skillId;
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevelConfigId() {
        return nextLevelConfigId;
    }

    public List<IConsume> getConsumes() {
        return consumes;
    }
}
