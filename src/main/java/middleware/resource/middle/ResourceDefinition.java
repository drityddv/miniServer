package middleware.resource.middle;

/**
 * @author : ddv
 * @since : 2019/5/10 下午2:55
 */

public class ResourceDefinition {

    private final String PATH_NAME = "src/main/resources/csv/";

    private final Class<?> clz;

    private String location;

    // 写死即可
    public ResourceDefinition(Class<?> clz) {
        this.clz = clz;
        location = PATH_NAME + "MapResource";
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
