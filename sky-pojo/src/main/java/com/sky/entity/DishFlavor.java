package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName dish_flavor
 */
@TableName(value ="dish_flavor")
@Data
public class DishFlavor implements Serializable {
    @TableId
    private Long id;

    private Long dishId;

    private String name;

    private String value;

    private static final long serialVersionUID = 1L;
}