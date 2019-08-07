package game.system.model.mbean;

import spring.SpringContext;

import javax.management.MXBean;

/**
 * @author : ddv
 * @since : 2019/8/6 10:30 PM
 */
@MXBean
public class MiniMBean implements MiniServerMBean {

    @Override
    public int getOnlinePlayerCount() {
        return SpringContext.getSessionManager().getSize();
    }

	@Override
	public int getInstanceCount() {
		return 0;
	}
}
