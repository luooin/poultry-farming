package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class Login {
    private Integer id;
    private String username;
    @Length(min = 6,max = 20,message = "密码长度在6-20之间")
    private String password;
    private Integer powerId;

    private Integer userId;
//    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String phone;
    private Integer is_default;
    private Integer status;
    private User user;
    private Power power;
    private String token;
}
