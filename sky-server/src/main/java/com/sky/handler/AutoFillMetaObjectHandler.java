package com.sky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.MyFillConstant;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/9 20:59
 * @description
 **/
@Component
@Slf4j
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("执行插入自动填充...");
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        this.setFieldValByName(MyFillConstant.CREATE_TIME, now, metaObject);
        this.setFieldValByName(MyFillConstant.CREATE_USER, id, metaObject);
        this.setFieldValByName(MyFillConstant.UPDATE_TIME, now, metaObject);
        this.setFieldValByName(MyFillConstant.UPDATE_USER, id, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("执行更新自动填充...");
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        this.setFieldValByName(MyFillConstant.UPDATE_TIME, now, metaObject);
        this.setFieldValByName(MyFillConstant.UPDATE_USER, id, metaObject);
    }
}
