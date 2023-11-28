package com.sky.mapper;

import com.sky.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.Orders
*/
@Mapper

public interface OrdersMapper extends BaseMapper<Orders> {

}




