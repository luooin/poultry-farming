package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 修改用户信息创建对象接收数据
 */
@Data
public class LogUser {
    private Integer id;
    @NotNull(message = "用户信息为空")
    private String userId;
    @NotBlank(message = "用户名为空")
    private String username;
    private Integer powerId;
    @NotBlank(message = "姓名为空")
    private String name;
    @Max(value = 100,message = "年龄最大为100")
    private Integer age;
    private Integer gender;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String phone;
}
