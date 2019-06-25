package game.center.item.resource;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import middleware.anno.Init;
import middleware.anno.MiniResource;

/**
 * @author : ddv
 * @since : 2019/5/31 下午5:25
 */
@MiniResource
public class Item {

    private long id;
    private String itemName;

    private String effectString;

    public static void main(String[] args) {
        Item item = new Item();
        Method[] declaredMethods = item.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                ReflectionUtils.invokeMethod(method, item);
            }
        }
    }

    @Init
    private void init() {

    }

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
