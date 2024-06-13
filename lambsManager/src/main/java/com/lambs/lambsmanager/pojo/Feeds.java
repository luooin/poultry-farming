package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Feeds {
    private Integer id;
    private String feedName;
    private String feedCount;
    private BigDecimal feedPrice;
    private String feedOrigin;
    private Integer feedStatus;
    private String feedPlace;
    private Date feedDate;
    private String describle;
    @NotNull(message = "用户信息为空")
    private Integer userId;
}
