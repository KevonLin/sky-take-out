package com.sky.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevonlin
 * @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void addCart(ShoppingCartDTO shoppingCartDTO) {
        Long setmealId = shoppingCartDTO.getSetmealId();
        Long dishId = shoppingCartDTO.getDishId();
        String dishFlavor = shoppingCartDTO.getDishFlavor();
        //需要根据user_id查询用户购物车
        Long userId = BaseContext.getCurrentId();
        //添加之前，查询是否存在
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId)
                .eq(setmealId != null, ShoppingCart::getSetmealId, setmealId)
                .eq(dishId != null, ShoppingCart::getDishId, dishId)
                .eq(!StringUtils.isEmpty(dishFlavor), ShoppingCart::getDishFlavor, dishFlavor);
        //存在number++
        ShoppingCart shoppingCart = shoppingCartMapper.selectOne(shoppingCartLambdaQueryWrapper);
        if (shoppingCart != null) {
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            //执行更新
            shoppingCartMapper.updateById(shoppingCart);
        } else {
            //不存在添加行
            shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            //判断添加的是菜品还是套餐
            if (dishId != null) {
                //菜品
                //购物车表中的表项一定不重复，口味重复问题上方已解决
                Dish dish = dishMapper.selectById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setDishId(dishId);
                shoppingCart.setDishFlavor(dishFlavor);
                shoppingCart.setAmount(dish.getPrice());
            } else {
                //套餐
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setSetmealId(setmealId);
                shoppingCart.setAmount(setmeal.getPrice());
            }
            //执行插入
            shoppingCartMapper.insert(shoppingCart);
        }
    }
}




