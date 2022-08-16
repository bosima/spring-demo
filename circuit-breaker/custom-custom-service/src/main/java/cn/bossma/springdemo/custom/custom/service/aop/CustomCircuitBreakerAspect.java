package cn.bossma.springdemo.custom.custom.service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
@Slf4j
public class CustomCircuitBreakerAspect {
    private static final Integer BREAK_LIMIT = 3;
    private static final Integer BACK_LIMIT = 3;
    private static Map<String, AtomicInteger> failedCount = new ConcurrentHashMap<>();
    private static Map<String, AtomicInteger> breakCount = new ConcurrentHashMap<>();

    @Around("execution(* cn.bossma.springdemo.custom.custom.service.integration..*(..))")
    public Object doServiceOperation(ProceedingJoinPoint pjp) throws Throwable {
        var signature = pjp.getSignature().toShortString();
        log.info("Invoke {}", signature);
        try {
            if (breakCount.getOrDefault(signature, new AtomicInteger(0)).intValue() < BACK_LIMIT) {
                if (failedCount.getOrDefault(signature, new AtomicInteger(0)).intValue() >= BREAK_LIMIT) {
                    breakCount.putIfAbsent(signature, new AtomicInteger(0));
                    breakCount.get(signature).incrementAndGet();
                    return null;
                }
            } else {
                failedCount.getOrDefault(signature, new AtomicInteger(0)).set(0);
                breakCount.getOrDefault(signature, new AtomicInteger(0)).set(0);
            }

            Object result = pjp.proceed();
            failedCount.getOrDefault(signature, new AtomicInteger(0)).set(0);
            breakCount.getOrDefault(signature, new AtomicInteger(0)).set(0);
            return result;
        } catch (Exception ex) {
            failedCount.putIfAbsent(signature, new AtomicInteger(0));
            failedCount.get(signature).incrementAndGet();
            throw ex;
        }
    }
}
