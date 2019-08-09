package game.publicsystem.alliance.packet;

/**
 * @author : ddv
 * @since : 2019/8/8 11:04 PM
 */

public class CM_HandlerInvite {
    private long inviteId;
    private int agreed;

    public int getAgreed() {
        return agreed;
    }

	public long getInviteId() {
		return inviteId;
	}
}
