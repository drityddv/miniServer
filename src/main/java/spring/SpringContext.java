package spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import game.base.executor.service.IMiniExecutorService;
import game.gm.service.GM_Command;
import game.gm.service.IGmService;
import game.scene.map.service.ISceneMapService;
import game.user.login.service.ILoginService;
import game.user.pack.service.IPackService;
import game.user.player.service.IPlayerService;
import middleware.dispatch.Dispatcher;
import middleware.manager.SessionManager;
import middleware.resource.storage.StorageManager;

/**
 * @author : ddv
 * @since : 2019/4/29 下午3:01
 */

@Component
public class SpringContext implements ApplicationContextAware {

    private static SpringContext instance;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private IMiniExecutorService miniExecutorService;

    // 业务service区

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ISceneMapService sceneMapService;

    @Autowired
    private IGmService gmService;

    @Autowired
    private GM_Command gmCommand;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private IPackService packService;

    public static Dispatcher getDispatcher() {
        return instance.dispatcher;
    }

    public static SessionManager getSessionManager() {
        return instance.sessionManager;
    }

    public static ILoginService getLoginService() {
        return instance.loginService;
    }

    public static ISceneMapService getSceneMapService() {
        return instance.sceneMapService;
    }

    public static IGmService getGmService() {
        return instance.gmService;
    }

    public static GM_Command getGmCommand() {
        return instance.gmCommand;
    }

    public static StorageManager getStorageManager() {
        return instance.storageManager;
    }

    public static IPlayerService getPlayerService() {
        return instance.playerService;
    }

    public static IPackService getPackService() {
        return instance.packService;
    }

    public static IMiniExecutorService getMiniExecutorService() {
        return instance.miniExecutorService;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
