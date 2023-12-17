package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
* @author kevonlin
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface SetmealService extends IService<Setmeal> {
    /*
     * @param setmealPageQueryDTO
     * @return com.sky.result.PageResult
     * @author kevonlin
     * @create 2023/12/12 12:41
     * @description 套餐分页查询
     **/
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /*
     * @param id
     * @return com.sky.vo.SetmealVO
     * @author kevonlin
     * @create 2023/12/12 20:48
     * @description 根据套餐ID查询套餐详细信息以及关联的菜品
     **/
    SetmealVO getSetmealById(Long id);

    /*
     * @param categoryId
     * @return java.util.List<com.sky.entity.Setmeal>
     * @author kevonlin
     * @create 2023/12/17 11:46
     * @description 根据分类id查询套餐
     **/
    List<Setmeal> getSetmealListByCategoryId(Long categoryId);

    /*
     * @param id
     * @return java.util.List<com.sky.vo.DishItemVO>
     * @author kevonlin
     * @create 2023/12/17 12:01
     * @description 根据套餐id查询包含的菜品列表
     **/
    List<DishItemVO> getDishItemVOListBySetMealId(Long id);
}
