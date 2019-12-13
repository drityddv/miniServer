package micro.constant;

/**
 * @author : ddv
 * @since : 2019/12/11 10:57 AM
 */

public enum MicroServiceEnum {
    /**
     * 匹配服务
     */
    match_service(1, "micro-match", false),
    /**
     * 服务组件
     */
    zookeeper_service(2, "micro.zookeeper-service", true),;

    MicroServiceEnum(int id, String serverName, boolean staticAddress) {
        this.id = id;
        this.serverName = serverName;
        this.staticAddress = staticAddress;
    }

    private int id;
    private String serverName;
    private boolean staticAddress;

	public int getId() {
		return id;
	}

	public String getServerName() {
		return serverName;
	}

	public boolean isStaticAddress() {
		return staticAddress;
	}
}
