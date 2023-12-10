package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/9 21:47
 * @description 菜品接口
 **/
@RestController
@RequestMapping("admin/dish")
@Slf4j
@Api("菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("添加菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        return dishService.addDish(dishDTO);
    }


    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO){
        return dishService.getDishPage(dishPageQueryDTO);
    }
}
