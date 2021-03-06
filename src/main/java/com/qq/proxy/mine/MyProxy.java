/**
 * 
 * @Date:2019年11月4日
 * 
 */
package com.qq.proxy.mine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.springframework.util.FileCopyUtils;

/**
 * @author liupan
 *
 */
public class MyProxy {

	// 这个地方我没有很明白，我试了"\r\n"好像也是可以的
	private static final String rt = "\r";

	public static Object newProxyInstance(MyClassLoader loader, Class<?> interfaces, MyInvocationHandler h)
			throws IllegalArgumentException {

		if (h == null) {
			throw new NullPointerException();
		}

		Method[] methods = interfaces.getMethods();

		StringBuffer proxyClassString = new StringBuffer();
		proxyClassString.append("package ").append(loader.getProxyClassPackage()).append(";").append(rt)
				.append("import java.lang.reflect.Method;").append(rt).append("public class $MyProxy0 implements ")
				.append(interfaces.getName()).append("{").append(rt).append("MyInvocationHandler h;").append(rt)
				.append("public $MyProxy0(MyInvocationHandler h){").append(rt).append("this.h = h;}").append(rt)
				.append(getMethodString(methods, interfaces)).append("}");

		String fileName = loader.getDir() + File.separator + "$MyProxy0.java";

		File myProxyFile = new File(fileName);
		try {
			compile(proxyClassString, myProxyFile);

			Class $myProxy0 = loader.findClass("$MyProxy0");

			Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);

			Object o = constructor.newInstance(h);

			return o;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void compile(StringBuffer proxyClassString, File myProxyFile) throws IOException {

		// 生成.java文件
		FileCopyUtils.copy(proxyClassString.toString().getBytes(), myProxyFile);

		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

		Iterable javaFileObjects = standardFileManager.getJavaFileObjects(myProxyFile);

		JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null,
				javaFileObjects);

		task.call();

		standardFileManager.close();
	}

	private static String getMethodString(Method[] methods, Class interfaces) {
		StringBuffer methodStringBuffer = new StringBuffer();

		for (Method method : methods) {
			methodStringBuffer.append("public void ").append(method.getName()).append("()").append("throws Throwable{")
					.append("Method method1 = ").append(interfaces.getName()).append(".class.getMethod(\"")
					.append(method.getName()).append("\", new Class[]{});").append("this.h.invoke(this,method1,null);}")
					.append(rt);
		}

		return methodStringBuffer.toString();
	}

}
