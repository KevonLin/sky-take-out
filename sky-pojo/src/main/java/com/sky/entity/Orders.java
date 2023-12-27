package com.sky.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    @TableId
    private Long id;

    private String number;

    private Integer status;

    private Long userId;

    private Long addressBookId;

    private Date orderTime;

    private Date checkoutTime;

    private Integer payMethod;

    private Integer payStatus;

    private BigDecimal amount;

    private String remark;

    private String phone;

    private String address;

    private String userName;

    private String consignee;

    private String cancelReason;

    private String rejectionReason;

    private LocalDateTime cancelTime;

    private LocalDateTime estimatedDeliveryTime;

    private Integer deliveryStatus;

    private LocalDateTime deliveryTime;

    private Integer packAmount;

    private Integer tablewareNumber;

    private Integer tablewareStatus;

    private static final long serialVersionUID = 1L;
}