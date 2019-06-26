package game.user.player.resource;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.base.game.player.PlayerModel;
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

    private Attribute attribute;

    @Init
    public void init() {
        String[] split = attributeString.split(",");
        attribute = new Attribute();
        attribute.setModel(PlayerModel.PLAYER_SELF);

        for (String temp : split) {
            String[] split1 = temp.split(":");
            AttributeType attributeType = AttributeType.getByName(split1[0]);
            attribute.addAttribute(attributeType, JodaUtil.convertFromString(Long.class, split1[1]));
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

    public Attribute getAttribute() {
        return attribute;
    }
}
