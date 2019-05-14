package middleware.resource.middle;

/**
 * @author : ddv
 * @since : 2019/5/10 下午2:55
 */

public class ResourceDefinition {

    private final String PATH_NAME = "src/main/resources/csv/";

    private final Class<?> clz;

    private String location;

    // 无奈 这里先写死吧,后续再改 2019-05-13 21:56:12
    public ResourceDefinition(Class<?> clz) {
        this.clz = clz;
        location = PATH_NAME + "MapResource";
        // MapResource anno = clz.getAnnotation(MapResource.class);
        // if (anno != null) {
        // // 这里默认用ClazzName做资源文件名
        // location = PATH_NAME + anno.name();
        // } else {
        // location = PATH_NAME + "MapResource";
        // }
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
