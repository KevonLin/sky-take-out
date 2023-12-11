package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
import com.sky.result.Result;

import java.util.List;

/**
* @author kevonlin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface CategoryService extends IService<Category> {

    /*
     * @param categoryPageQueryDTO
     * @return com.sky.result.PageResult
     * @author kevonlin
     * @create 2023/12/1 11:22
     * @description 分类的分压查询
     **/
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /*
     * @param type
     * @return com.sky.result.Result<java.util.List<com.sky.entity.Category>>
     * @author kevonlin
     * @create 2023/12/1 17:41
     * @description 根据类型查询分类列表
     **/
    List<Category> getByType(Integer type);
}
