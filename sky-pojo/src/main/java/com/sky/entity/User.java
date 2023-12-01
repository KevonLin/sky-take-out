package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId
    private Long id;

    private String openid;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private String avatar;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}