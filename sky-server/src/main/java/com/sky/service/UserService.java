package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
* @author kevonlin
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface UserService extends IService<User> {

    /*
     * @param userLoginDTO
     * @return com.sky.entity.User
     * @author kevonlin
     * @create 2023/12/15 20:40
     * @description 微信登录实现
     **/
    User login(UserLoginDTO userLoginDTO);
}
