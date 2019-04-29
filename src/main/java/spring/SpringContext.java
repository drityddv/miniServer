package spring;

import middleware.dispatch.Dispatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	private void init() {
		instance = this;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Dispatcher getDispatcher() {
		return instance.dispatcher;
	}
}
