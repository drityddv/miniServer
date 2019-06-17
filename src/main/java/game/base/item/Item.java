package game.base.item;

import middleware.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/5/31 下午5:25
 */
@MiniResource
public class Item {

    private long id;
    private String itemName;

    // get and set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
