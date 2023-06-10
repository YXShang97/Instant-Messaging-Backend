package com.yuxin.messaging.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Log4j2
public class LogAspect {
    @Around("execution(* com.yuxin.messaging.controller.*.*(..))")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date start = new Date();
        boolean exceptionThrown = false;
        String className = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            exceptionThrown = true;
            throw throwable;
        } finally {
            Date end = new Date();
            long duration = end.getTime() - start.getTime();
            log.info("Executed {}.{} in {} ms, exception thrown: {}",
                    className, methodName, duration, exceptionThrown);
        }
    }
}
