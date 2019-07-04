package game.base.executor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.account.IAccountExecutorService;
import middleware.dispatch.HandlerInvoke;

/**
 * @author : ddv
 * @since : 2019/6/18 下午11:25
 */

@Component
public class MiniExecutorService implements IMiniExecutorService {

    private final static Logger logger = LoggerFactory.getLogger(MiniExecutorService.class);

    @Autowired
    private IAccountExecutorService accountExecutorService;

    @Override
    public void handle(HandlerInvoke handlerInvoke, Object param, int modIndex, Object packet) {
        accountExecutorService.addTask(modIndex, packet.getClass().getSimpleName(), () -> {
            handlerInvoke.invoke(param, packet);
        });
    }

}
