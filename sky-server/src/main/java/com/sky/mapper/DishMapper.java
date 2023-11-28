package com.sky.mapper;

import com.sky.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【dish(菜品)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




