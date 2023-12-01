package com.sky.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author kevonlin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    IPage<Map> pageQuery(IPage<Category> categoryIPage, @Param("categoryPageQueryDTO") CategoryPageQueryDTO categoryPageQueryDTO);
}




