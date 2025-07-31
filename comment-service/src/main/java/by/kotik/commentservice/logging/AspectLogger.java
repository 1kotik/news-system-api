package by.kotik.commentservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AspectLogger {
    @Pointcut("within(by.kotik.commentservice.service.*)")
    public void isServiceLayer() {
    }

    @Pointcut("within(by.kotik.commentservice.controller.*)")
    public void isControllerLayer() {
    }

    @Pointcut("within(by.kotik.commentservice.listener.*)")
    public void isListenerLayer() {}

    @AfterReturning(value = "isServiceLayer() || isControllerLayer() || isListenerLayer()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        if (result != null) {
            log.info("Invoked {}. Returned: {}", joinPoint.getSignature().getName(), result);
        } else {
            log.info("Invoked {}. Returned void", joinPoint.getSignature().getName());
        }
    }

    @AfterThrowing(value = "isServiceLayer() || isControllerLayer() || isListenerLayer()",
            throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Invoked {}. Thrown: {}. Message {}",
                joinPoint.getSignature().getName(), ex.getClass(), ex.getMessage());
    }
}
