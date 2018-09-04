package org.yugzan.aop.interceptor;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceAspect {
	
    @Pointcut("execution(** org.yugzan..*Service.*(..))")
    public void servicePointCut() {}
    
    @Before("servicePointCut()")
    public void auth() {
        System.out.println("auth user");
    }
    
    @Before("execution(** org.yugzan..SomeService.doService(String)) && args(user)")
    public void printMsg(String user) {
        System.out.println("User is:" + user);
    }

    @After("servicePointCut()")
    public void welcome() {
        System.out.println("welcome service.");
    }
}
