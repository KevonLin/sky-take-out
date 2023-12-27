package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName address_book
 */
@TableName(value ="address_book")
@Data
public class AddressBook implements Serializable {
    @TableId
    private Long id;

    private Long userId;

    private String consignee;

    private String sex;

    private String phone;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtCode;

    private String districtName;

    private String detail;

    private String label;

    private Integer isDefault;

    private static final long serialVersionUID = 1L;
}