package game.publicsystem.alliance.packet;

/**
 * @author : ddv
 * @since : 2019/8/5 3:27 PM
 */

public class CM_HandlerApplication {
    private int operationTypeId;
    private long applicationId;
    private int agreed;

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public int getAgreed() {
        return agreed;
    }
}
