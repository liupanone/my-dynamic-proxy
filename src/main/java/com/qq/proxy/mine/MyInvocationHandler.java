/**
 * 
 * @Date:2019年10月29日
 * 
 */
package com.qq.proxy.mine;

import java.lang.reflect.Method;

/**
 * @author liupan
 *
 */
public interface MyInvocationHandler {

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
