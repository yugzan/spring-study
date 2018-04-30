package org.yugzan.aop.interceptor;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.yugzan.aop.AuthMethod;

public class AuthProxyInterceptor implements MethodInterceptor {
	

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		
		if(method.isAnnotationPresent(AuthMethod.class)) {
			//check user
			
		}
		return invocation.proceed();
		
//		return null;
	}
	

}
