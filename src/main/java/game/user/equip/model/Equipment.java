package game.user.equip.model;

import java.util.List;

import game.base.game.attribute.Attribute;
import game.user.equip.constant.EquipPosition;
import game.user.equip.recource.EquipResource;
import game.user.item.base.model.AbstractItem;
import game.user.item.resource.ItemResource;
import spring.SpringContext;

/**
 * 装备
 *
 * @author : ddv
 * @since : 2019/6/26 下午2:20
 */

public class Equipment extends AbstractItem {

    /**
     * 装备的自身属性
     */
    private List<Attribute> attributeList;

    private EquipPosition equipPosition;

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);
        EquipResource equipResource = SpringContext.getEquipService().getEquipResource(configId);
        attributeList = equipResource.getAttributes();
        equipPosition = equipResource.getEquipPosition();
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public EquipPosition getEquipPosition() {
        return equipPosition;
    }

	@Override
	public String toString() {
		return "Equipment{" +
				"equipPosition=" + equipPosition +
				", configId=" + configId +
				'}';
	}
}
