package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Supplies {
    private Integer id;
    private String suppliesName;
    private String suppliesUnit;
    private String suppliesCount;
    private String suppliesPrice;
    private String provider;
    @Length(min = 1,max = 6,message = "联系人名称最长为6")
    private String suppliesAttn;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String attnPhone;
    private Integer isMar;
    private String marCount;
    private String suppliesCause;
    @NotNull(message = "用户信息为空")
    private Integer userId;



}
