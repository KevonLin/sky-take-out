package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.ShoppingCart;
import com.sky.service.ShoppingCartService;
import com.sky.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author kevonlin
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-11-28 11:25:27
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




