package xyz.ibudai.validate.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xyz.ibudai.validate.core.ValidateDetector;
import xyz.ibudai.validate.core.annotation.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class ValidateAspect {

    @Pointcut("@annotation(validate) && @annotation(xyz.ibudai.validate.core.annotation.Validate) ")
    public void pointcut(Validate validate) {
    }

    @Around(value = "pointcut(validate)", argNames = "joinPoint, validate")
    public Object around(ProceedingJoinPoint joinPoint, Validate validate) {
        try {
            Object[] args = joinPoint.getArgs();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // Get target method
            Method method = signature.getMethod();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                ValidateDetector.detect(args[i], parameters[i]);
            }

            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
