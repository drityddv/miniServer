package middleware.resource.middle;

import middleware.anno.Resource;

/**
 * @author : ddv
 * @since : 2019/5/10 下午2:55
 */

public class ResourceDefinition {

    private final String PATH_NAME = "src/main/resources/csv/";

    private final Class<?> clz;

    private String location;

    public ResourceDefinition(Class<?> clz) {
        this.clz = clz;
        Resource anno = clz.getAnnotation(Resource.class);
        if (anno != null) {
            // 这里默认用ClazzName做资源文件名
            location = PATH_NAME + clz.getSimpleName();
        }
    }

	public String getPATH_NAME() {
		return PATH_NAME;
	}

	public Class<?> getClz() {
		return clz;
	}

	public String getLocation() {
		return location;
	}
}
