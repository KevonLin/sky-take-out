package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

//    IPage<Map> pageQuery(IPage<Category> categoryIPage, @Param("categoryPageQueryDTO") CategoryPageQueryDTO categoryPageQueryDTO);
}




