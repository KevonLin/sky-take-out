package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author kevonlin
 * @description 针对表【dish(菜品)】的数据库操作Service
 * @createDate 2023-11-28 11:25:27
 */
public interface DishService extends IService<Dish> {

    /*
     * @param dishDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/9 22:00
     * @description 添加菜品
     **/
    void addDish(DishDTO dishDTO);

    /*
     * @param dishPageQueryDTO 页面请求数据
     * @return com.sky.result.Result<com.sky.vo.DishVO>
     * @author kevonlin
     * @create 2023/12/10 9:26
     * @description 菜品分页查询
     **/
    PageResult getDishPage(DishPageQueryDTO dishPageQueryDTO);

    /*
     * @param ids 要删除的菜品ID
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/10 11:12
     * @description 批量删除菜品
     **/
    void deleteDish(List<Long> ids);

    /*
     * @param id
     * @return com.sky.vo.DishVO
     * @author kevonlin
     * @create 2023/12/11 20:55
     * @description 根据菜品ID获取相关菜品的详细信息以及口味信息
     **/
    DishVO getDishByIdWithFlavor(Long id);

    /*
     * @param categoryId
     * @return com.sky.entity.Dish
     * @author kevonlin
     * @create 2023/12/11 23:15
     * @description 根据类别ID获取
     **/
    List<Dish> getByCategoryId(Long categoryId);

    /*
     * @param status
     * @param id
     * @return void
     * @author kevonlin
     * @create 2023/12/11 23:39
     * @description 修改菜品售卖状态
     **/
    void toggleStatus(Integer status, Long id);

    /*
     * @param categoryId
     * @return java.util.List<com.sky.vo.DishVO>
     * @author kevonlin
     * @create 2023/12/17 11:30
     * @description 根据分类id查询菜品
     **/
    List<DishVO> getDishByCategoryIdWithFlavors(Long categoryId);
}
