package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalTime;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/9 19:27
 * @description 自定义切面类，实现公共字段自动填充处理逻辑
 * 该种切入方式要求切入点方法的第一个参数为实体类，需要将注解标注到Mapper
 * MyBatis-Plus无法标注到Mapper
 * 采用@TableFiled注解进行属性注入
 **/
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*
     * @param
     * @return void
     * @author kevonlin
     * @create 2023/12/9 19:29
     * @description 自动天充切入点
     * 定义切入点后在通知的注解中可以直接引用切入点的方法名
     **/
    @Pointcut("execution(* com.sky..*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {
    }

    /*
     * @param joinPoint 连接点
     * @return void
     * @author kevonlin
     * @create 2023/12/9 19:50
     * @description
     **/
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始公共字段自动填充...");

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) return;
        Object entity = args[0];

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType type = annotation.value();
        try {
            Method setCreateTime = null, setCreateUser = null, setUpdateTime = null, setUpdateUser = null;
            if (type == OperationType.INSERT) {
                setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalTime.class);
                setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            }
            setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalTime.class);
            setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            if (setCreateTime != null) setCreateTime.invoke(entity, LocalTime.now());
            if (setCreateUser != null) setCreateUser.invoke(entity, BaseContext.getCurrentId());
            setUpdateTime.invoke(entity, LocalTime.now());
            setUpdateUser.invoke(entity, BaseContext.getCurrentId());

        } catch (Exception e) {
            log.error("自动填充失败");
        }
    }
}
