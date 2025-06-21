package org.dreams.backend.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class ItemAspect {
    private final Map<String, Integer> methodCallCounts = new ConcurrentHashMap<>();
    private final Map<String, Double> methodCallTimes = new ConcurrentHashMap<>();

    @Around("execution(* org.dreams.backend.controller.*.*(..))")
    public Object trackMethodCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        methodCallCounts.merge(methodName, 1, Integer::sum);
        System.out.println(methodName + " execution count " + methodCallCounts.get(methodName));
        return joinPoint.proceed();
    }

    @Around("execution(* org.dreams.backend.controller.*.*(..))")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName + " executed in " + executionTime + "ms");
        long count = methodCallCounts.getOrDefault(methodName, 0);
        if (count > 0) {
            Double callTime = methodCallTimes.getOrDefault(methodName, 0.0);
            Double newAvg = (callTime * (count - 1) + executionTime) / count;
            methodCallTimes.put(methodName, newAvg);
            System.out.println(methodName + " average is " + newAvg + "ms");
        }

        return proceed;
    }

    @AfterReturning(
            pointcut = "execution(* org.dreams.backend.controller..*(..))",
            returning = "result")
    public void logReturnedValue(Object result) {
        System.out.println("Method returned: " + result);
    }
}
