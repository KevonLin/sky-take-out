package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

/**
* @author kevonlin
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface ShoppingCartService extends IService<ShoppingCart> {

    /*
     * @param shoppingCartDTO
     * @return void
     * @author kevonlin
     * @create 2023/12/27 14:45
     * @description 添加购物车
     **/
    void addCart(ShoppingCartDTO shoppingCartDTO);
}
