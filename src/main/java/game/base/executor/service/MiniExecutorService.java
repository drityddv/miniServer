package game.base.executor.service;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.constant.ExecutorConst;
import middleware.dispatch.HandlerInvoke;
import net.model.USession;
import utils.ClassUtil;
import utils.SimpleUtil;

/**
 * @author : ddv
 * @since : 2019/6/18 下午11:25
 */

@Component
public class MiniExecutorService implements IMiniExecutorService {

    private final static Logger logger = LoggerFactory.getLogger(MiniExecutorService.class);

    @Autowired
    private AccountExecutor accountExecutor;

    @Autowired
    private SceneExecutor sceneExecutor;

    @Override
    public void handle(HandlerInvoke handlerInvoke, USession session, Object packet, int id) {
        ThreadPoolExecutor targetThread = getTargetThread(session, packet, id);
        targetThread.submit(new Runnable() {
            @Override
            public void run() {
                logger.info("线程池接受任务[{}]", packet.getClass());
                handlerInvoke.invoke(SimpleUtil.getPlayerFromSession(session), packet);
            }
        });
    }

    private ThreadPoolExecutor getTargetThread(USession session, Object packet, int id) {
        if (id > ExecutorConst.MAP_THREAD_ID) {
            return sceneExecutor.getThread(ClassUtil.getFieldByName(packet, "mapId", Long.class));
        }
        return accountExecutor.getThread(SimpleUtil.getAccountIdFromSession(session));
    }

}
