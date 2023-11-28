package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.User;
import com.sky.service.UserService;
import com.sky.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author kevonlin
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-11-28 11:25:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




