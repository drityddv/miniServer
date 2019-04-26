package middleware.manager;

import net.utils.ProtoStuffUtil;

import java.util.HashMap;

/**
 * 临时使用的id与Clazz对应关联表
 *
 * @author : ddv
 * @since : 2019/4/26 下午4:42
 */

public class ClazzManager {

	private static HashMap<Integer, String> clazzMap = new HashMap<>();

	static {
		clazzMap.put(1, "middleware.model.User");
	}

	public static Class getClazz(Integer id) {
		String clazzName = clazzMap.get(id);
		Class<?> defClass = null;
		if (clazzName == null) {
			return null;
		}
		try {
			defClass = Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return defClass;
	}

	public static <T> T readObjectFromBytes(byte[] bytes, Class<T> clazz) throws ClassNotFoundException {
		if (clazz == null) {
			throw new ClassNotFoundException();
		}
		return ProtoStuffUtil.deserialize(bytes, clazz);
	}

	public static Object readObjectById(byte[] bytes, int id) throws ClassNotFoundException {
		return readObjectFromBytes(bytes, getClazz(id));
	}
}
