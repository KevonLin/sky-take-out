package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/27 14:39
 * @description 购物车相关接口
 **/
@RestController
@RequestMapping("user/shoppingCart")
@Slf4j
@Api(tags = "购物车相关接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /*
     * @param shoppingCartDTO
     * @return com.sky.result.Result
     * @author kevonlin
     * @create 2023/12/27 14:42
     * @description 添加购物车
     **/
    @PostMapping("add")
    @ApiOperation("添加购物车")
    public Result addCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车，商品信息为：{}", shoppingCartDTO);
        shoppingCartService.addCart(shoppingCartDTO);
        return Result.success();
    }
}
