package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author kevonlin
 * @description 针对表【dish(菜品)】的数据库操作Mapper
 * @createDate 2023-11-28 11:25:27
 * @Entity com.sky.entity.Dish
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    /*
     * @param dishPageQueryDTO
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.sky.vo.DishVO>
     * @author kevonlin
     * @create 2023/12/10 10:18
     * @description 联表分页查询
     **/
    IPage<Map> pageQuery(IPage<DishVO> page, @Param("dishPageQueryDTO") DishPageQueryDTO dishPageQueryDTO);

    /*
     * @param id 套餐ID
     * @return java.util.List<com.sky.vo.DishItemVO>
     * @author kevonlin
     * @create 2023/12/17 12:17
     * @description 根据套餐id查询包含的菜品列表
     **/
    List<DishItemVO> getDishItemVOListBySetMealId(Long id);
}




