package ru.sber.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Аспект, который выполняется, если метод пемечен аннотацией @NotEmpty и выбрасывает исключения
 */
@Aspect
@Component
public class NotEmptyAspect {
    private Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());
    @Around("@annotation(NotEmpty)")
    public Object validateNotEmptyArguments(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?>[] parameterTypes = signature.getParameterTypes();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                throw new IllegalArgumentException("Ошибка: " + (i + 1) + "-й аргумент является null");
            }
            if (parameterTypes[i].equals(String.class) && ((String) args[i]).isEmpty()) {
                throw new IllegalArgumentException("Ошибка: " + (i + 1) + "-й строчный аргумент является пустым");
            }
            //рассмотрен отдельный случай для List
            if (parameterTypes[i].equals(List.class) && args[i] instanceof List && ((List<?>) args[i]).isEmpty()
            || parameterTypes[i].equals(Collection.class) && args[i] instanceof Collection && ((Collection<?>) args[i]).isEmpty()) {
                throw new IllegalArgumentException("Ошибка: " + (i + 1) + "-й аргумент-список является пустым");
            }
        }
        return joinPoint.proceed();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
