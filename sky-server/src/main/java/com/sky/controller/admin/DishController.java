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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*
     * @param dishDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/11 20:12
     * @description 添加菜品
     **/
    @PostMapping
    @ApiOperation("添加菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDish(dishDTO);
        return Result.success();
    }

    /*
     * @param dishPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author kevonlin
     * @create 2023/12/11 20:12
     * @description 菜品分页查询
     **/
    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO){
        PageResult dishPage = dishService.getDishPage(dishPageQueryDTO);
        return Result.success(dishPage);
    }

    /*
     * @param ids
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/11 20:12
     * @description 批量删除菜品
     **/
    @Transactional
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids){
        log.info("菜品批量删除：{}", ids);
        if (ids.size() == 0) {
            return Result.error("未选中菜品");
        }
        dishService.deleteDish(ids);
        return Result.success();
    }
}
