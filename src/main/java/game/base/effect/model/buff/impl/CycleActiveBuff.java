package game.base.effect.model.buff.impl;

import game.base.effect.model.buff.BaseCreatureBuff;

/**
 * 周期调度[延时取消,定时执行,周期执行...]
 *
 * @author : ddv
 * @since : 2019/7/23 8:21 PM
 */

public class CycleActiveBuff extends BaseCreatureBuff {
    // 总共需要执行次数
    protected int periodCount;
    // 剩余执行次数
    protected int remainCount;
	// 执行频率
    private long frequencyTime;

    @Override
    public void initBuffJob() {

    }
}
