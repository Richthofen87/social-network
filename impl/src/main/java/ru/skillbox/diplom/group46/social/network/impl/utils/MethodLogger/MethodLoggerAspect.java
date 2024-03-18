package ru.skillbox.diplom.group46.social.network.impl.utils.MethodLogger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MethodLoggerAspect {

    @Around("@annotation(MethodLogger)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Метод " + methodName + " начался");
        Object result = joinPoint.proceed();
        log.info("Метод " + methodName + " закончился");
        return result;
    }
}