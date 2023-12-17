package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐浏览接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /*
     * @param categoryId
     * @return com.sky.result.Result<java.util.List<com.sky.entity.Setmeal>>
     * @author kevonlin
     * @create 2023/12/17 11:38
     * @description 根据分类id查询套餐
     **/
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setMealCache", key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        List<Setmeal> list = setmealService.getSetmealListByCategoryId(categoryId);
        return Result.success(list);
    }

    /*
     * @param id
     * @return com.sky.result.Result<java.util.List<com.sky.vo.DishItemVO>>
     * @author kevonlin
     * @create 2023/12/17 11:38
     * @description 根据套餐id查询包含的菜品列表
     **/
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品列表")
    @Cacheable(cacheNames = "dishCache", key = "#id")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        List<DishItemVO> dishes= setmealService.getDishItemVOListBySetMealId(id);
        return Result.success(dishes);
    }
}
