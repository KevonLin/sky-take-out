package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @TableName setmeal_dish
 */
@TableName(value ="setmeal_dish")
@Data
public class SetmealDish implements Serializable {
    @TableId
    private Long id;

    private Long setmealId;

    private Long dishId;

    private String name;

    private BigDecimal price;

    private Integer copies;

    private static final long serialVersionUID = 1L;
}