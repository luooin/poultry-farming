package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class Breed {
    private Integer id;
    private String breedName;
    private String breedImg;
    private String breedKind;
    private String breedCount;
    private Date breedStartdate;
    private String breedTitle;
    private String breedContent;
    private Integer figure;
    @NotNull(message = "用户信息为空")
    private Integer userId;
}
