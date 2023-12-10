package com.sky.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kevonlin
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        String categoryName = categoryPageQueryDTO.getName();
        Integer categoryType = categoryPageQueryDTO.getType();
        int pageNum = categoryPageQueryDTO.getPage();
        int pageSize = categoryPageQueryDTO.getPageSize();

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(categoryName), Category::getName, categoryName)
                .like(categoryType != null, Category::getType, categoryType);

        IPage<Category> categoryIPage = new Page<>(pageNum, pageSize);
        IPage<Category> categoryIPageMap = categoryMapper.selectPage(categoryIPage, wrapper);
//        IPage<Map> categoryIPageMap = categoryMapper.pageQuery(categoryIPage, categoryPageQueryDTO);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(categoryIPageMap.getTotal());
        pageResult.setRecords(categoryIPageMap.getRecords());
        return Result.success(pageResult);
    }

    @Override
    public Result<List<Category>> getByType(Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType, type);
        List<Category> categories = categoryMapper.selectList(wrapper);

        return Result.success(categories);
    }
}




