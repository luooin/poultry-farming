package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class Medical {
    private Integer id;
    private String medicalName;
    private String medicalType;
    private String medicalFirm;
    private String medicalCount;
    private String medicalPrice;
    private Date buyTime;
    private Integer isLend;
    private Integer lendUserId;
    private User user;
    private Date lendTime;
    private String lendCount;
    private String returnCount;
    private Integer isMar;
    @NotNull(message = "用户信息不为空")
    private Integer userId;
}
