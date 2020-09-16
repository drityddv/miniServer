import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2020/2/27 12:54 PM
 */

public class User {

    private static Map<Object, Object> map = new HashMap<>();
    private int id;
    private int level;

    public static User valueOf(int id, int level) {
        User user = new User();
        user.id = id;
        user.level = level;
        return user;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public static void main(String[] args) {
       List<Integer> list = new ArrayList<>();
       list.add(1);
       list.add(2000);
       list.add(1000);
       list.add(100);

       list.sort(Integer::compareTo);
       System.out.println(1);
    }

    public static <T> T get(Object key) {
        return (T)map.get(key);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", level=" + level + '}';
    }

    public static void log(Object param1, Object param2) {
        System.out.println("log");
    }

    public static void log(Object... param) {
        System.out.println("logs2");
    }
}
