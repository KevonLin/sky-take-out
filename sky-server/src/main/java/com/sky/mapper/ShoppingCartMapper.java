package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.ShoppingCart
*/
@Mapper

public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




