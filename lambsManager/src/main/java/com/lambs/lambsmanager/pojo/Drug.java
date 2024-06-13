package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Drug {
    private Integer id;
    private String drugName;
    private String drugImg;
    private String category;
    private String specification;
    private String drugCount;
    private String batchNumber;
    private Date manufactureDate;
    private Date expirationDate;
    private String supplier;
    private String buyerCount;
    private BigDecimal buyerPrice;
    private String userName;
    @NotNull(message = "用户信息不能为空")
    private Integer userId;
}
