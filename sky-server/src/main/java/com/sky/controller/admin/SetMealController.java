package com.sky.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(cacheNames = {"setMealCache", "dishCache"}, allEntries = true)
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐：{}", setmealDTO);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealService.save(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
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
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * @param ids
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/12 14:35
     * @description 批量删除套餐
     **/
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = {"setMealCache", "dishCache"}, allEntries = true)
    public Result deleteBatchSet(@RequestParam List<Long> ids) {
        log.info("批量删除套餐:{}", ids);
        // 起售中的套餐不能删除
        ids.forEach(id -> {
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.eq(Setmeal::getId, id)
                    .eq(Setmeal::getStatus, StatusConstant.ENABLE);
            if (setmealService.count(setmealLambdaQueryWrapper) > 0) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        // 批量删除套餐
        setmealService.removeBatchByIds(ids);
        // 删除套餐关联菜品表中关联的菜品
        ids.forEach(id -> {
            LambdaUpdateWrapper<SetmealDish> setmealDishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            setmealDishLambdaUpdateWrapper.eq(SetmealDish::getSetmealId, id);
            setmealDishService.remove(setmealDishLambdaUpdateWrapper);
        });
        return Result.success();
    }

    /*
     * @param id
     * @return com.sky.result.Result<com.sky.vo.SetmealVO>
     * @author kevonlin
     * @create 2023/12/12 20:58
     * @description 根据ID查询套餐
     **/
    @GetMapping("{id}")
    @ApiOperation("根据ID查询套餐")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id) {
        log.info("根据ID查询套餐:{}", id);
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    /*
     * @param setmealDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/12 21:05
     * @description 修改套餐并且重新插入关联菜品表
     **/
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = {"setMealCache", "dishCache"}, allEntries = true)
    public Result updateSetMeal(@RequestBody SetmealDTO setmealDTO) {
        // 1.修改套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealService.updateById(setmeal);
        // 2.根据套餐ID删除关联菜品表中的菜品
        setmealDishService.removeById(setmealDTO.getId());
        // 3.根据传入的关联菜品重新插入 在Controller中调用saveBatch方法批量插入
        setmealDishService.saveBatch(setmealDTO.getSetmealDishes());
        return Result.success();
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改套餐状态")
    @CacheEvict(cacheNames = {"setMealCache", "dishCache"}, allEntries = true)
    public Result toggleSetMealStatus(@PathVariable Integer status, Long id) {
        LambdaUpdateWrapper<Setmeal> setmealLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealLambdaUpdateWrapper.eq(Setmeal::getId, id)
                .set(Setmeal::getStatus, status);
        setmealService.update(setmealLambdaUpdateWrapper);
        return Result.success();
    }
}
