package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
import com.sky.result.Result;

/**
* @author kevonlin
* @description 针对表【dish(菜品)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface DishService extends IService<Dish> {

    /*
     * @param dishDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/9 22:00
     * @description 添加菜品
     **/
    Result addDish(DishDTO dishDTO);

    /*
     * @param dishPageQueryDTO 页面请求数据
     * @return com.sky.result.Result<com.sky.vo.DishVO>
     * @author kevonlin
     * @create 2023/12/10 9:26
     * @description 菜品分页查询
     **/
    Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO);
}
