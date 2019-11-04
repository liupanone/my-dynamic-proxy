/**
 * 
 * @Date:2019年11月4日
 * 
 */
package com.qq.proxy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * @author liupan
 *
 */
public class ProxyTest {

	public static void main(String[] args) throws Throwable {
		Man man = new Q();
		ManHandler manHandler = new ManHandler(man);

		Man proxyMan = (Man) Proxy.newProxyInstance(man.getClass().getClassLoader(), new Class[] { Man.class },
				manHandler);
		System.out.println(proxyMan.getClass().getName());
		proxyMan.findObject();

		// createProxyClassFile(Man.class);
	}

	// 该方法生成代理类，导出在项目的根目录下
	private static void createProxyClassFile(Class c) {

		// 需要注意的是sun.misc这个包，它虽然在jdk中，但是在eclipse中需要配置一下才能使用
		byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[] { c });

		try {
			FileOutputStream out = new FileOutputStream("$Proxy0.class");
			out.write(data);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
