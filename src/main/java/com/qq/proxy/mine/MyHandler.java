/**
 * 
 * @Date:2019年10月29日
 * 
 */
package com.qq.proxy.mine;

import java.lang.reflect.Method;

import com.qq.proxy.Man;

/**
 * @author liupan
 *
 */
public class MyHandler implements MyInvocationHandler {

	private Man man;

	public MyHandler(Man man) {
		this.man = man;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		before();
		method.invoke(man, args);
		after();

		return null;
	}

	public void before() {
		System.out.println("before find you, I will try to do better");
	}

	public void after() {
		System.out.println("after find you, I will do your best");
	}

}
