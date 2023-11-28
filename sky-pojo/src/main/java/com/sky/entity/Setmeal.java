package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName setmeal
 */
@TableName(value ="setmeal")
@Data
public class Setmeal implements Serializable {
    @TableId
    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    private Integer status;

    private String description;

    private String image;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private static final long serialVersionUID = 1L;
}