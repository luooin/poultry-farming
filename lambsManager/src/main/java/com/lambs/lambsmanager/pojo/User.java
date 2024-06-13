package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    @Min(value = 10, message = "年龄必须大于10岁")
    @Max(value = 150, message = "年龄必须小于100岁")
    private Integer age;
    private Integer gender;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone;
    private Login login;
}
