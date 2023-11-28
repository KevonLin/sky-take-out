package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Orders;
import com.sky.service.OrdersService;
import com.sky.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author kevonlin
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-11-28 11:25:27
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




