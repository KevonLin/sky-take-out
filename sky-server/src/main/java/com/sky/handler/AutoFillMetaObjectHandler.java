package com.sky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/9 20:59
 * @description
 **/
@Component
@Slf4j
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    LocalTime now = LocalTime.now();
    Long id = BaseContext.getCurrentId();

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("执行插入自动填充...");
        this.setFieldValByName(AutoFillConstant.SET_CREATE_TIME, now, metaObject);
        this.setFieldValByName(AutoFillConstant.SET_CREATE_USER, id, metaObject);
        this.setFieldValByName(AutoFillConstant.SET_UPDATE_TIME, now, metaObject);
        this.setFieldValByName(AutoFillConstant.SET_UPDATE_USER, id, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("执行更新自动填充...");
        this.setFieldValByName(AutoFillConstant.SET_UPDATE_TIME, now, metaObject);
        this.setFieldValByName(AutoFillConstant.SET_UPDATE_USER, id, metaObject);
    }
}
