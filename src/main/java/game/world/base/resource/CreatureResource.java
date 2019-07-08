package game.world.base.resource;

import java.util.List;

import game.base.game.attribute.Attribute;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.ResourceUtil;

/**
 * @author : ddv
 * @since : 2019/7/8 上午9:25
 */
@MiniResource
public class CreatureResource {
    /**
     * id
     */
    private int configId;
    /**
     * 对象类型
     */
    private int objectType;
    /**
     * 名称
     */
    private String objectName;
    /**
     * 等级
     */
    private int level;
    private int nextLevelConfigId;
    private List<Attribute> attributeList;
    private String attributeString;

    private String rewardString;

    @Init
    public void init() {
        attributeList = ResourceUtil.initAttrs(attributeString);
    }

    // get and set
    public int getConfigId() {
        return configId;
    }

    public int getObjectType() {
        return objectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevelConfigId() {
        return nextLevelConfigId;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

}
