package com.sky.controller.admin;


import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/1 11:06
 * @description 分类管理
 **/
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*
     * @param categoryDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/12/1 11:08
     * @description 添加分类
     **/
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        Category category = new Category();
        //属性拷贝
        BeanUtils.copyProperties(categoryDTO, category);
        //分类状态默认为禁用状态0
        category.setStatus(StatusConstant.DISABLE);
        //设置创建时间、修改时间、创建人、修改人
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryService.save(category);
        return Result.success();
    }

    /*
     * @param categoryPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author kevonlin
     * @create 2023/12/1 11:08
     * @description 分页分类查询
     **/
    @GetMapping("page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * @param id
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/12/1 11:09
     * @description 删除分类
     **/
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.removeById(id);
        return Result.success();
    }

    /*
     * @param categoryDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/12/1 11:09
     * @description 修改分类
     **/
    @PutMapping
    @ApiOperation("修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类，参数:{}", categoryDTO);
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryService.updateById(category);
        return Result.success();
    }

    /*
     * @param status
     * @param id
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/12/1 11:09
     * @description 修改分类状态
     **/
    @PostMapping("status/{status}")
    @ApiOperation("启用禁用分类")
    public Result<String> toggleCategory(Long id, @PathVariable Integer status) {
        Category dbCategory = categoryService.getById(id);
        Category category = new Category();
        BeanUtils.copyProperties(dbCategory, category);
        category.setStatus(status);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryService.updateById(category);
        return Result.success();
    }

    /*
     * @param type
     * @return com.sky.result.Result<java.util.List<com.sky.entity.Category>>
     * @author kevonlin
     * @create 2023/12/1 11:10
     * @description 根据类型查询分类
     **/
    @GetMapping("list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type) {
        List<Category> typeList = categoryService.getByType(type);
        return Result.success(typeList);
    }
}
