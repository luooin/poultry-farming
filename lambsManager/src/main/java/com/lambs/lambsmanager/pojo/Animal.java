package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class Animal {
    private Integer id;
    private String animalName;
    @Min(value = 1,message = "参数错误")
    @Max(value = 2,message = "参数错误")
    private Integer animalSex;
    private Date animalDate;
    @Min(value = 0,message = "参数错误")
    @Max(value = 2,message = "参数错误")
    private Integer animalStatus;
    private double animalWeight;
    @Min(value = 0,message = "参数错误")
    @Max(value = 1,message = "参数错误")
    private Integer isBreed;
    private Integer feedsId;
    private Feeds feeds;
    private Integer drugId;
    private Drug drug;
    @NotNull(message = "用户信息为空")
    private Integer userId;
}
