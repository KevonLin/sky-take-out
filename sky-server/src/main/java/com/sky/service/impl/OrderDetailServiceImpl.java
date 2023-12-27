package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import com.sky.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
* @author kevonlin
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-11-28 11:25:27
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




