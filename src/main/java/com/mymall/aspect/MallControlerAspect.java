package com.mymall.aspect;

import com.mymall.common.ServerResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MallControlerAspect {

    private final static Logger logger = LoggerFactory.getLogger(MallControlerAspect.class);

    @Pointcut("execution(* com.mymall.controller..*Controller.*(..))")
    public void doController() {
    }


    @Before("doController()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //TODO
        logger.info("url: {}, 方法: {}, 请求ip: {},类和方法: {} , 参数:{}", request.getRequestURL(),
                request.getMethod(), request.getRemoteHost(), request.getClass(), request.getParameterMap());

        System.out.println("AOP Before Advice...");
    }

    @After("doController()")
    public void doAfter(JoinPoint joinPoint) {

    }

    @AfterReturning(pointcut = "doController()", returning = "returnVal")
    public void afterReturn(JoinPoint joinPoint, Object returnVal) {
        if (null != returnVal) {
            if (returnVal instanceof ServerResponse) {

            }
        }

        System.out.println("AOP AfterReturning Advice:" + returnVal);
    }

    @AfterThrowing(pointcut = "doController()", throwing = "error")
    public void afterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("AOP AfterThrowing Advice..." + error);
        System.out.println("AfterThrowing...");
    }

    @Around("doController()")
    public void around(ProceedingJoinPoint pjp) {
        System.out.println("AOP Aronud before...");
        try {
            pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("AOP Aronud after...");
    }


}
