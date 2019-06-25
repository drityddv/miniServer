// package spring.scan;
//
// import org.springframework.beans.factory.config.BeanDefinitionHolder;
// import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
// import org.springframework.core.env.Environment;
// import org.springframework.core.io.ResourceLoader;
// import org.springframework.stereotype.Component;
//
// import java.util.Set;
//
/// **
// * @author : ddv
// * @since : 2019/6/17 下午7:33
// */
// @Component
// public class MiniScanner extends ClassPathBeanDefinitionScanner {
//
// public MiniScanner(BeanDefinitionRegistry registry) {
// super(registry);
// }
//
// public MiniScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
// super(registry, useDefaultFilters);
// }
//
// public MiniScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
// super(registry, useDefaultFilters, environment);
// }
//
// public MiniScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment,
// ResourceLoader resourceLoader) {
// super(registry, useDefaultFilters, environment, resourceLoader);
// }
//
// @Override
// protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
// Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
// System.out.println(1);
// return beanDefinitionHolders;
// }
// }
