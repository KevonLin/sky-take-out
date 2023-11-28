package com.sky.mapper;

import com.sky.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kevonlin
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.User
*/
@Mapper

public interface UserMapper extends BaseMapper<User> {

}




