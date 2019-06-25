// package spring.scan;
//
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
// import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
// import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.ApplicationContextAware;
// import org.springframework.stereotype.Component;
//
/// **
// * 额外自定义的spring扫描
// *
// * @author : ddv
// * @since : 2019/6/17 下午7:18
// */
// @Component
// public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {
//
// private ApplicationContext applicationContext;
//
// @Override
// public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
// MiniScanner miniScanner = new MiniScanner((BeanDefinitionRegistry)beanFactory);
// miniScanner.setResourceLoader(this.applicationContext);
// miniScanner.scan("game", "db", "middleware", "spring");
// }
//
// @Override
// public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
// this.applicationContext = applicationContext;
// }
// }
