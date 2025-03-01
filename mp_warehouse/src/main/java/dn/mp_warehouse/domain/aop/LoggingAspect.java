package dn.mp_warehouse.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* dn.mp_warehouse.domain.service.impl..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Начало выполнения метода {}",joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* dn.mp_warehouse.domain.service.impl..*(..))",
            throwing = "ex", argNames = "ex,joinPoint")
    public void logAfterThrowing(Throwable ex,JoinPoint joinPoint) {
        log.error("Метод {} выбросил исключение {0}",joinPoint.getSignature().getName(),ex);
    }

    @AfterReturning(pointcut = "execution(* dn.mp_warehouse.domain.service.impl..*(..))")
    public void logAfterSuccessExecution(JoinPoint joinPoint){
        log.info("Метод {} выполнился успешно",joinPoint.getSignature().getName());
    }



    
}
