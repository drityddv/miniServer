package spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import game.base.ebus.EventBus;
import game.base.executor.scene.ISceneExecutorService;
import game.base.executor.service.IMiniExecutorService;
import game.gm.service.GM_Command;
import game.gm.service.IGmService;
import game.scene.fight.service.IFightService;
import game.scene.npc.service.NpcManager;
import game.user.equip.service.IEquipService;
import game.user.login.service.ILoginService;
import game.user.mapinfo.service.IMapInfoService;
import game.user.pack.service.IPackService;
import game.user.player.service.IPlayerService;
import game.world.base.service.IWorldService;
import game.world.neutral.neutralMap.service.INeutralMapService;
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

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ISceneExecutorService sceneExecutorService;

    // 地图专专用
    @Autowired
    private IWorldService worldService;

    @Autowired
    private IMapInfoService mapInfoService;

    @Autowired
    private INeutralMapService neutralMapService;

    // 业务service区
    @Autowired
    private ILoginService loginService;

    @Autowired
    private IGmService gmService;

    @Autowired
    private GM_Command gmCommand;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private IPackService packService;

    @Autowired
    private IEquipService equipService;

    @Autowired
    private NpcManager npcManager;

    @Autowired
    private IFightService fightService;

    public static Dispatcher getDispatcher() {
        return instance.dispatcher;
    }

    public static SessionManager getSessionManager() {
        return instance.sessionManager;
    }

    public static ILoginService getLoginService() {
        return instance.loginService;
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

    public static EventBus getEventBus() {
        return instance.eventBus;
    }

    public static IEquipService getEquipService() {
        return instance.equipService;
    }

    public static ISceneExecutorService getSceneExecutorService() {
        return instance.sceneExecutorService;
    }

    public static IWorldService getWorldService() {
        return instance.worldService;
    }

    public static NpcManager getNpcManager() {
        return instance.npcManager;
    }

    public static IMapInfoService getMapInfoService() {
        return instance.mapInfoService;
    }

    public static INeutralMapService getNeutralMapService() {
        return instance.neutralMapService;
    }

    public static IFightService getFightService() {
        return instance.fightService;
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
