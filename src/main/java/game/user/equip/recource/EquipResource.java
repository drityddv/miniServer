package game.user.equip.recource;

import java.util.ArrayList;
import java.util.List;

import game.base.game.attribute.Attribute;
import game.base.game.attribute.AttributeType;
import game.user.equip.constant.EquipPosition;
import middleware.anno.Init;
import middleware.anno.MiniResource;
import utils.JodaUtil;

/**
 * @author : ddv
 * @since : 2019/6/28 下午8:23
 */
@MiniResource
public class EquipResource {
    private int configId;
    /**
     * 这个对应的是itemResource中的configId
     */
    private int itemConfigId;

    private List<Attribute> attributes;
    private String attributeString;

    /**
     * 装备的位置信息
     */
    private EquipPosition equipPosition;
    private String equipPositionString;

    @Init
    public void init() {
        analysisAttrs();
        analysisPosition();
    }

    private void analysisPosition() {
        equipPosition = EquipPosition.getPosition(equipPositionString);
    }

    private void analysisAttrs() {
        attributes = new ArrayList<>();
        String[] attrs = attributeString.split(",");
        attributes = new ArrayList<>();
        for (String attr : attrs) {
            String[] params = attr.split(":");
            attributes.add(Attribute.valueOf(AttributeType.getByName(params[0]),
                JodaUtil.convertFromString(Integer.class, params[1])));
        }
    }

    public int getConfigId() {
        return configId;
    }

    public int getItemConfigId() {
        return itemConfigId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public EquipPosition getEquipPosition() {
        return equipPosition;
    }
}
