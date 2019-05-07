package game.gm.packet;

/**
 * @author : ddv
 * @since : 2019/5/7 下午2:30
 */

public class CM_GmCommand {

	private String methodAndParams;

	public String getMethodAndParams() {
		return methodAndParams;
	}

	public void setMethodAndParams(String methodAndParams) {
		this.methodAndParams = methodAndParams;
	}

	@Override
	public String toString() {
		return "CM_GmCommand{" +
				"methodAndParams='" + methodAndParams + '\'' +
				'}';
	}
}
