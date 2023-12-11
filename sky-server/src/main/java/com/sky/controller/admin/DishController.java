package com.sky.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

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
    public Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO) {
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
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        if (ids.size() == 0) {
            return Result.error("未选中菜品");
        }
        dishService.deleteDish(ids);
        return Result.success();
    }

    /*
     * @param id
     * @return com.sky.result.Result<com.sky.vo.DishVO>
     * @author kevonlin
     * @create 2023/12/11 20:42
     * @description 根据ID查询菜品信息
     **/
    @GetMapping("{id}")
    @ApiOperation("根据ID查询菜品信息")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("根据ID查询菜品信息:{}", id);
        DishVO dishVO = dishService.getDishByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /*
     * @param dishDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/11 22:00
     * @description 修改菜品
     **/
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        // 1.修改菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishService.updateById(dish);
        // 2.修改口味表
        // 2.1删除原有口味
        LambdaUpdateWrapper<DishFlavor> dishFlavorLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        dishFlavorLambdaUpdateWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
        dishFlavorService.remove(dishFlavorLambdaUpdateWrapper);
        // 2.2添加传入口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        for (DishFlavor flavor : flavors) {
            /* 一个一个存
            DishFlavor dishFlavor = new DishFlavor();
            BeanUtils.copyProperties(flavor, dishFlavor);
            dishFlavor.setDishId(dishDTO.getId());
            dishFlavorService.save(dishFlavor);
             */
            flavor.setDishId(dishDTO.getId());
        }
        dishFlavorService.saveBatch(flavors);
        return Result.success();
    }

    /*
     * @param categoryId
     * @return com.sky.result.Result<com.sky.entity.Dish>
     * @author kevonlin
     * @create 2023/12/11 23:13
     * @description 根据分类ID查询菜品信息
     **/
    @GetMapping("list")
    @ApiOperation("根据分类ID查询菜品信息")
    public Result<List<Dish>> getByCategoryId(Long categoryId) {
        List<Dish> dishes = dishService.getByCategoryId(categoryId);
        return Result.success(dishes);
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改菜品售卖状态")
    public Result toggleStatus(@PathVariable Integer status, Long id) {
        dishService.toggleStatus(status, id);
        return Result.success();
    }
}
