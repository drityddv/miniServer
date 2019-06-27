package game.user.player.resource;

import java.util.ArrayList;
import java.util.List;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/6/25 下午2:21
 */

@MiniResource
public class PlayerResource {

    private int level;
    private long experience;
    private String attributeString;
    private List<Attribute> attributeList;

    @Init
    public void init() {
        String[] split = attributeString.split(",");
        attributeList = new ArrayList<>();
        for (String attrStrings : split) {
            String[] attrWords = attrStrings.split(":");
            AttributeType attributeType = AttributeType.getByName(attrWords[0]);
            long value = JodaUtil.convertFromString(Long.class, attrWords[1]);
            Attribute attribute = Attribute.valueOf(attributeType, value);
            attributeList.add(attribute);
        }
    }

    // get and set

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getAttributeString() {
        return attributeString;
    }

    public void setAttributeString(String attributeString) {
        this.attributeString = attributeString;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }
}
