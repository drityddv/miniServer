package resource.model;

/**
 * @author : ddv
 * @since : 2019/5/10 下午2:55
 */

public class ResourceDefinition {

    private static final String PATH_NAME = "src/main/resources/csv/";

    private final Class<?> clz;

    private String location;

    public ResourceDefinition(Class<?> clz) {
        this.clz = clz;
        location = PATH_NAME + clz.getSimpleName();
    }

    public Class<?> getClz() {
        return clz;
    }

    public String getLocation() {
        return location;
    }
}
