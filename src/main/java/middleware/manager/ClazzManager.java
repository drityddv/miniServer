package middleware.manager;

import net.utils.ProtoStuffUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 临时使用的id与Clazz对应关联表
 *
 * @author : ddv
 * @since : 2019/4/26 下午4:42
 */

public class ClazzManager {

	private static final Logger logger = LoggerFactory.getLogger(ClazzManager.class);

	private static Map<Integer, String> clazzMap;

	private static final String MESSAGE_XML = "src/main/resources/message.xml";

	static {
		try {
			loadResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadResource() throws JDOMException, IOException {
		Map<Integer, String> recourseMap = new HashMap<>();
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document;
		document = saxBuilder.build(MESSAGE_XML);
		Element child = document.getRootElement();

		List<Element> children = child.getChildren("message");

		for (int i = 0; i < children.size(); i++) {
			Element element = children.get(i);
			Integer id = element.getAttribute("id").getIntValue();
			String value = element.getAttribute("value").getValue();

			if (recourseMap.containsKey(id)) {
				logger.error("协议资源文件配置重复，重复id[{}]", id);
				throw new RuntimeException("协议资源文件加载失败！");
			}
			recourseMap.put(id, value);
		}

		clazzMap = recourseMap;
		logger.info("协议资源文件加载完成，总共加载了[{}]条数据", children.size());
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
