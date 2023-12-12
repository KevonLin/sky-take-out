package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;

/**
* @author kevonlin
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface SetmealService extends IService<Setmeal> {
    /*
     * @param setmealPageQueryDTO
     * @return com.sky.result.PageResult
     * @author kevonlin
     * @create 2023/12/12 12:41
     * @description 套餐分页查询
     **/
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
