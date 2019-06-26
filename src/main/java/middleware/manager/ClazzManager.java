package middleware.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.utils.ProtoStuffUtil;

/**
 * id与Clazz对应关联表
 *
 * @author : ddv
 * @since : 2019/4/26 下午4:42
 */
@Component
public class ClazzManager {

    private static final Logger logger = LoggerFactory.getLogger(ClazzManager.class);
    // private static final String MESSAGE_XML = "miniServer/message.xml";
    private static final String MESSAGE_XML = "src/main/resources/message.xml";
    private static Map<Integer, String> clazzMap;
    private static Map<Class<?>, Integer> idMap;

    static {
        try {
            loadResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadResource() throws JDOMException, IOException, ClassNotFoundException {
        Map<Integer, String> recourseMap = new HashMap<>();
        Map<Class<?>, Integer> clazzIdMap = new HashMap<>();

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document;
        document = saxBuilder.build(MESSAGE_XML);
        Element child = document.getRootElement();

        List<Element> children = child.getChildren("message");

        for (int i = 0; i < children.size(); i++) {
            Element element = children.get(i);
            Integer id = element.getAttribute("id").getIntValue();
            String value = element.getAttribute("value").getValue();
            Class clazz = Class.forName(value);

            if (recourseMap.containsKey(id)) {
                logger.error("协议资源文件配置重复，重复id[{}]", id);
                throw new RuntimeException("协议资源文件加载失败！");
            }

            if (clazzIdMap.containsKey(clazz)) {
                logger.error("协议资源文件配置重复，重复class[{}]", value);
                throw new RuntimeException("协议资源文件加载失败！");
            }

            recourseMap.put(id, value);
            clazzIdMap.put(clazz, id);
        }

        idMap = clazzIdMap;
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

    public static int getIdByClazz(Class<?> clazz) {
        Integer id = idMap.get(clazz);
        // 序列号从1开始 0暂时用来异常占位
        return id == null ? 0 : id;
    }

    public static boolean isContain(Class clazz) {
        return idMap.containsKey(clazz);
    }
}
