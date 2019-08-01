package game.world.instance.model;

/**
 * @author : ddv
 * @since : 2019/7/31 6:28 PM
 */

public class InstanceParam {
    // 是否是单人副本
    private boolean single;
    // 单人副本为playerId 多人为地图id
    private long instanceId;

    public InstanceParam(boolean single, long instanceId) {
        this.single = single;
        this.instanceId = instanceId;
    }

    public static InstanceParam valueOf(Boolean single, long instanceId) {
        return new InstanceParam(single, instanceId);
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

}
