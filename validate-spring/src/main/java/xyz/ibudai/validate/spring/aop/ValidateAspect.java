package xyz.ibudai.validate.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.ibudai.validate.core.ValidateDetector;
import xyz.ibudai.validate.core.annotation.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

@Aspect
@Component
public class ValidateAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ValidateAspect.class);

    @Pointcut("@annotation(validate) && @annotation(xyz.ibudai.validate.core.annotation.Validate) ")
    public void pointcut(Validate validate) {
    }

    @Around(value = "pointcut(validate)", argNames = "joinPoint, validate")
    public Object around(ProceedingJoinPoint joinPoint, Validate validate) {
        try {
            Object[] args = joinPoint.getArgs();
            boolean serialize = validate.serialize();
            boolean skipFailed = validate.skipFailed();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // Get target method
            Method method = signature.getMethod();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                Parameter param = parameters[i];
                if (Collection.class.isAssignableFrom(arg.getClass()) && skipFailed) {
                    // filter the none success validate object
                    Collection<?> col = (Collection<?>) arg;
                    for (Object o : col) {
                        try {
                            ValidateDetector.detect(o, param, serialize);
                        } catch (Exception e) {
                            LOG.error("validation failed", e);
                            col.remove(o);
                        }
                    }
                    args[i] = col;
                    continue;
                }

                // Normally validation
                ValidateDetector.detect(arg, param, serialize);
            }

            return joinPoint.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
