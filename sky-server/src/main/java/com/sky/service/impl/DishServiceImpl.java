package com.sky.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.mapper.DishMapper;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kevonlin
 * @description 针对表【dish(菜品)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
                dishFlavorMapper.insert(flavor);
            }
        }
        return Result.success();
    }

    @Override
    public Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO) {
        /* 两次查询并手动封装VO
        Integer categoryId = dishPageQueryDTO.getCategoryId();
        String name = dishPageQueryDTO.getName();
        Integer status = dishPageQueryDTO.getStatus();
        int pageNum = dishPageQueryDTO.getPage();
        int pageSize = dishPageQueryDTO.getPageSize();

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO, dish);

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(categoryId != null, Dish::getCategoryId, categoryId)
                .like(!StringUtils.isEmpty(name), Dish::getName, name)
                .like(status != null, Dish::getStatus, status)
                .orderByDesc(Dish::getCreateTime);
        IPage<Dish> dishIPage = new Page<>(pageNum, pageSize);
        IPage<Dish> dishIPageMap = dishMapper.selectPage(dishIPage, wrapper);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(dishIPageMap.getTotal());
        List<Dish> records = dishIPageMap.getRecords();
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish rs : records) {
            DishVO dishVO = new DishVO();
            LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryLambdaQueryWrapper.eq(Category::getId, rs.getCategoryId());
            Category category = categoryMapper.selectOne(categoryLambdaQueryWrapper);
            String categoryName = category.getName();
            BeanUtils.copyProperties(rs, dishVO);
            dishVO.setCategoryName(categoryName);
            dishVOList.add(dishVO);
        }
        pageResult.setRecords(dishVOList);
        return Result.success(pageResult);
         */

        //自定义SQL语句实现联表查询
        IPage<DishVO> page = new Page<>(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        IPage<Map> pageMap = dishMapper.pageQuery(page, dishPageQueryDTO);
        PageResult pageResult = new PageResult(pageMap.getTotal(), pageMap.getRecords());
        return Result.success(pageResult);
    }
}




