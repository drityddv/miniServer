package utils;

import game.scene.map.packet.CM_EnterMap;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author : ddv
 * @since : 2019/5/17 下午3:29
 */

public class ClassUtil {

    /**
     * 注入对象的普通字段,具体哪些需要注入的字段与字段值,使用者自己传递,并且下标需要对应
     *
     * @param object
     * @param fieldList
     * @param values
     */
    public static void insertFields(Object object, List<Field> fieldList, List<String> values) {
        insertValue(object, fieldList, values);
    }

    private static void insertValue(Object object, List<Field> fieldList, List<String> values) {
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            String value = values.get(i);

            field.setAccessible(true);
            Class<?> type = field.getType();

            try {
                field.set(object, JodaUtil.convertFromString(type, value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入字段 按照class定义的字段顺序注入,注意值的传递顺序
     *
     * @param object
     * @param values
     */
    public static void insertDefaultFields(Object object, List<String> values) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        insertValue(object, Arrays.asList(declaredFields), values);
    }

    public static <T> T getFieldByName(Object object,String fieldName,Class<T> type){
		Field[] declaredFields = object.getClass().getDeclaredFields();
		for(Field field:declaredFields){
			field.setAccessible(true);
			if(field.getName().equals(fieldName)){
				try {
					return (T) field.get(object);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static void main(String[] args){
		CM_EnterMap cm =new CM_EnterMap();
		cm.setMapId(1);

		Long mapId = getFieldByName(cm, "map1Id", Long.class);
		System.out.println(mapId);
	}
}
