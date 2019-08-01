package spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ebus.service.EventBus;
import game.base.buff.service.BuffService;
import game.base.effect.service.IEffectService;
import game.base.executor.account.IAccountExecutorService;
import game.base.executor.scene.ISceneExecutorService;
import game.base.item.service.IItemService;
import game.base.manager.SessionManager;
import game.common.service.ICommonService;
import game.dispatch.Dispatcher;
import game.gm.service.GM_Command;
import game.gm.service.IGmService;
import game.role.equip.service.IEquipService;
import game.role.player.service.IPlayerService;
import game.role.skill.service.ISkillService;
import game.system.ISystemService;
import game.user.login.service.ILoginService;
import game.user.mapinfo.service.IMapInfoService;
import game.user.pack.service.IPackService;
import game.world.base.service.CreatureManager;
import game.world.base.service.IWorldService;
import game.world.fight.service.IFightService;
import game.world.instance.service.IInstanceService;
import game.world.mainCity.service.IMainCityService;
import game.world.neutral.neutralMap.service.INeutralMapService;
import net.server.Server;
import quartz.service.QuartzService;
import redis.service.RedisService;
import resource.service.StorageManager;

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
    private Server server;

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private ISystemService systemService;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private IEffectService effectService;

    @Autowired
    private IFightService fightService;

    @Autowired
    private IAccountExecutorService accountExecutorService;

    @Autowired
    private ISceneExecutorService sceneExecutorService;

    // 地图专专用
    @Autowired
    private IWorldService worldService;

    @Autowired
    private IMapInfoService mapInfoService;

    @Autowired
    private INeutralMapService neutralMapService;

    @Autowired
    private IMainCityService mainCityService;

    @Autowired
    private IInstanceService instanceService;

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
    private ISkillService skillService;

    @Autowired
    private CreatureManager creatureManager;

    @Autowired
    private BuffService buffService;

    @Autowired
    private RedisService redisService;

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

    public static EventBus getEventBus() {
        return instance.eventBus;
    }

    public static IEquipService getEquipService() {
        return instance.equipService;
    }

    public static ISkillService getSkillService() {
        return instance.skillService;
    }

    public static ISceneExecutorService getSceneExecutorService() {
        return instance.sceneExecutorService;
    }

    public static IWorldService getWorldService() {
        return instance.worldService;
    }

    public static CreatureManager getCreatureManager() {
        return instance.creatureManager;
    }

    public static IMapInfoService getMapInfoService() {
        return instance.mapInfoService;
    }

    public static INeutralMapService getNeutralMapService() {
        return instance.neutralMapService;
    }

    public static ICommonService getCommonService() {
        return instance.commonService;
    }

    public static IFightService getFightService() {
        return instance.fightService;
    }

    public static IAccountExecutorService getAccountExecutorService() {
        return instance.accountExecutorService;
    }

    public static Server getServer() {
        return instance.server;
    }

    public static QuartzService getQuartzService() {
        return instance.quartzService;
    }

    public static IItemService getItemService() {
        return instance.iItemService;
    }

    public static ISystemService getSystemService() {
        return instance.systemService;
    }

    public static IMainCityService getMainCityService() {
        return instance.mainCityService;
    }

    public static IInstanceService getInstanceService() {
        return instance.instanceService;
    }

    public static IEffectService getEffectService() {
        return instance.effectService;
    }

    public static BuffService getBuffService() {
        return instance.buffService;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static RedisService getRedisService() {
        return instance.redisService;
    }

    @PostConstruct
    private void init() {
        instance = this;
    }
}
