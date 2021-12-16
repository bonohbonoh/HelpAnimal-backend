package com.jeonggolee.helpanimal.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.jeonggolee.helpanimal..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        log.info("START: {}", joinPoint.toLongString());
        try {
            return  joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END: {} ({}ms)", joinPoint.toLongString(), timeMs);
        }

    }
}
