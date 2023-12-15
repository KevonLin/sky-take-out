package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.mapper.UserMapper;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevonlin
 * @description 针对表【user(用户信息)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        // 获取OpenId
        String openId = getOpenId(userLoginDTO.getCode());
        // 若为null获取失败，登陆失败
        if (openId == null) throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        // 根据openid查询数据库中是否存在，不存在则为新用户，创建新用户
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getOpenid, openId);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        // 用户不存在，创建新用户
        if (user == null) {
            user = new User();
            user.setOpenid(openId);
            // 自动填充字段
            // user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenId(String code) {
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> body = new HashMap<>();
        body.put("appid", weChatProperties.getAppid());
        body.put("secret", weChatProperties.getSecret());
        body.put("js_code", code);
        body.put("grant_type", "authorization_code");
        String response = HttpClientUtil.doGet(WX_LOGIN, body);

        JSONObject jsonObject = JSON.parseObject(response);
        return jsonObject.getString("openid");
    }
}




