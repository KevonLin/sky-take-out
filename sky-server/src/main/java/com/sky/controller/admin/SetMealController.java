package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/12 12:15
 * @description 套餐相关接口
 **/
@RestController
@RequestMapping("admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetMealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    /*
     * @param setmealDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/12 13:00
     * @description 添加套餐
     **/
    @PostMapping
    @ApiOperation("添加套餐")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐：{}", setmealDTO);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealService.save(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishService.saveBatch(setmealDishes);
        return Result.success();
    }

    /*
     * @param setmealPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author kevonlin
     * @create 2023/12/12 12:58
     * @description 套餐分页查询
     **/
    @GetMapping("page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
