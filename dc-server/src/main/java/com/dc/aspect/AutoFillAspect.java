package com.dc.aspect;

import com.dc.annotation.AutoFill;
import com.dc.constant.AutoFillConstant;
import com.dc.context.BaseContext;
import com.dc.enumeration.OperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.dc.mapper.*.*(..)) && @annotation(com.dc.annotation.AutoFill)")
    private void pointcut() {};

    @Before("pointcut()")
    public void autoFillPointcut(JoinPoint joinPoint) {
        // get operation type
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFillAnnotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFillAnnotation.value();

        // get method arguments
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        // auto fill
        Object entity = args[0];
        LocalDateTime currentTime = LocalDateTime.now();
        Long currentUser = BaseContext.getCurrentId();

        if (operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(entity, currentTime);
                setUpdateTime.invoke(entity, currentTime);
                setCreateUser.invoke(entity, currentUser);
                setUpdateUser.invoke(entity, currentUser);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (operationType == OperationType.UPDATE) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity, currentTime);
                setUpdateUser.invoke(entity, currentUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
