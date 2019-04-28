package spring;

import middleware.manager.ClazzManager;

/**
 * @author : ddv
 * @since : 2019/4/28 下午3:28
 */

public class Main {

	public static void main(String[] args){
		Class clazz = ClazzManager.getClazz(1);
		System.out.println(clazz);

	}
}
