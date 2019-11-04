/**
 * 
 * @Date:2019年11月4日
 * 
 */
package com.qq.proxy.mine;

import com.qq.proxy.Man;
import com.qq.proxy.Q;

/**
 * @author liupan
 *
 */
public class ProxyTest {

	public static void main(String[] args) throws Throwable {
		Man man = new Q();

		MyHandler myHandler = new MyHandler(man);

		// 注意路径和包名
		MyClassLoader classLoader = new MyClassLoader(
				"E:\\eclipse\\workspace\\my-dynamic-proxy\\src\\main\\java\\com\\qq\\proxy\\mine", "com.qq.proxy.mine");

		Man proxyMan = (Man) MyProxy.newProxyInstance(classLoader, Man.class, myHandler);
		System.out.println(proxyMan.getClass().getName());
		proxyMan.findObject();

	}
}
