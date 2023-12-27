package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author kevonlin
 * @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
        implements DishFlavorService {
}




