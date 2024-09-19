package com.example.demo.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReflectionLogsAspect {

    @Around("@annotation(com.example.demo.aop.annotation.ReflectionLogs)")
    public Object reflectionLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        //TODO.. Logging
        return joinPoint.proceed();
    }

}
