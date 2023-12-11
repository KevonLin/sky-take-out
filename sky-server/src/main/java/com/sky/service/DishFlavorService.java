package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.DishFlavor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.vo.DishVO;

import java.util.List;

/**
* @author kevonlin
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface DishFlavorService extends IService<DishFlavor> {
}
