package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName category
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {
    @TableId
    private Long id;

    private Integer type;

    private String name;

    private Integer sort;

    private Integer status;

    private LocalDateTime createTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

    private static final long serialVersionUID = 1L;
}