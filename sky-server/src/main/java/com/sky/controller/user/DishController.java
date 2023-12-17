package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;

    /*
     * @param categoryId
     * @return com.sky.result.Result<java.util.List<com.sky.vo.DishVO>>
     * @author kevonlin
     * @create 2023/12/17 11:37
     * @description 根据分类id查询菜品
     **/
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    @Cacheable(cacheNames = "dishesCache", key = "#categoryId")
    public Result<List<DishVO>> list(Long categoryId) {
        List<DishVO> list = dishService.getDishByCategoryIdWithFlavors(categoryId);
        return Result.success(list);
    }

}
