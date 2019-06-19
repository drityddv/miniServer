package game.base.executor;

import java.util.concurrent.ThreadFactory;

/**
 * @author : ddv
 * @since : 2019/6/17 下午10:43
 */

public class NameThreadFactory implements ThreadFactory {

    private String threadName;

    private int index;

    public NameThreadFactory(String threadName, int index) {
        this.threadName = threadName;
        this.index = index;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadName + "-" + index);
    }
}
