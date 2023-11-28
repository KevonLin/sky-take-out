package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Category;
import com.sky.service.CategoryService;
import com.sky.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author kevonlin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-11-28 11:25:27
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




