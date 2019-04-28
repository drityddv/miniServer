package spring;

import middleware.anno.HandlerAnno;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

/**
 * @author : ddv
 * @since : 2019/4/28 下午3:28
 */

public class Main {

	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		String[] names = ctx.getBeanDefinitionNames();
		for (String name : names) {
			Object bean = ctx.getBean(name);
			Class<?> beanClass = bean.getClass();
			Method[] methods = beanClass.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.isAnnotationPresent(HandlerAnno.class)) {
					System.out.println(method);
				}
			}
		}
	}
}
