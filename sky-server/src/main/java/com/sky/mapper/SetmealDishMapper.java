package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.SetmealDish
*/
@Mapper

public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




