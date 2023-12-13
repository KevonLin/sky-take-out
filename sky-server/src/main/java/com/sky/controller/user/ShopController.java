package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/14 1:53
 * @description 用户端店铺相关接口
 **/
@RestController("userShopController")
@RequestMapping("user/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {
    public static final String SHOP_STATUS = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

        /*
     * @param
     * @return com.sky.result.Result<java.lang.Integer>
     * @author kevonlin
     * @create 2023/12/14 1:52
     * @description 获取营业状态
     **/
    @GetMapping("status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("获取到的营业状态为:{}", status);
        return Result.success(status);
    }
}
