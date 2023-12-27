package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.OrderDetail
*/
@Mapper

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




