package com.sam.demo.config;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author sam
 * @date 10/29/19 16:25
 */
@Aspect
@Component
public class AspectConfig {
    @Pointcut("execution(public * com.sam.demo.api.*.*(..))")
    public void actionLog() {
    }

    @Before("actionLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Method method = clazz.getMethod(methodName);
        if(null != method&&method.getAnnotation(ApiInfomation.class)!=null){
            ApiInfomation apiInfomation = method.getAnnotation(ApiInfomation.class);
            System.out.println(apiInfomation.applicationName());
            System.out.println(apiInfomation.methodType());
        }
        System.out.println(joinPoint);
    }


}
