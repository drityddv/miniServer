package spring;

import javax.annotation.PostConstruct;

import middleware.dispatch.Dispatcher;
import middleware.manager.SessionManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import game.gm.service.GM_Command;
import game.gm.service.IGmService;
import game.scene.map.service.ISceneMapService;
import game.user.login.service.ILoginService;

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

	//业务service区

	@Autowired
	private ILoginService loginService;

	@Autowired
	private ISceneMapService sceneMapService;

	@Autowired
	private IGmService gmService;

	@Autowired
	private GM_Command gmCommand;

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

	public static GM_Command getGmCommand(){
		return instance.gmCommand;
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
