package com.sky.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.SetmealDishMapper;
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
    private SetmealDishMapper setmealDishMapper;
//    @Autowired
//    private CategoryMapper categoryMapper;

    @Override
    public void addDish(DishDTO dishDTO) {
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
    }

    @Override
    public PageResult getDishPage(DishPageQueryDTO dishPageQueryDTO) {
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
        return new PageResult(pageMap.getTotal(), pageMap.getRecords());
    }

    @Override
    public void deleteDish(List<Long> ids) {
        // 炫耀操作的表: dish dish_flavor setmeal_dish
        // 1.判断是否在售卖
        for (Long id : ids) {
            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishLambdaQueryWrapper.eq(Dish::getId, id)
                    .eq(Dish::getStatus, StatusConstant.ENABLE);
            if (dishMapper.selectCount(dishLambdaQueryWrapper) > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 2.根据DishID在setmeal_dish中查询是否有关联套餐
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getDishId, ids);
        Long count = setmealDishMapper.selectCount(setmealDishLambdaQueryWrapper);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 3.删除菜品数据
        dishMapper.deleteBatchIds(ids);
        // 4.删除菜品相关口味
        List<DishFlavor> dishFlavors = null;
        for (Long id : ids) {
            /* 删除需要多条SQL语句
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);
            dishFlavorMapper.delete(dishFlavorLambdaQueryWrapper);
             */
            // 将ID封装到List中使用DeleteBatchIds删除口味
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);
            dishFlavors = dishFlavorMapper.selectList(dishFlavorLambdaQueryWrapper);
        }
        if (dishFlavors != null) {
            dishFlavorMapper.deleteBatchIds(dishFlavors);
        }
    }

    @Override
    public DishVO getDishByIdWithFlavor(Long id) {
        DishVO dishVO = new DishVO();
        // 1.查询到菜品详细信息
        Dish dish = dishMapper.selectById(id);
        // 2.装如VO
        BeanUtils.copyProperties(dish, dishVO);
        // 3.根据菜品ID查询相关口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(dishFlavorLambdaQueryWrapper);
        // 4.装如VO
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, categoryId);
        return dishMapper.selectList(dishLambdaQueryWrapper);
    }

    @Override
    public void toggleStatus(Integer status, Long id) {
        Dish dish = dishMapper.selectById(id);
        dish.setStatus(status);
        dishMapper.updateById(dish);
    }
}




