package com.lambs.lambsmanager.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Fang {
    private Integer id;
    @NotNull(message = "放养名称为空")
    @NotBlank(message = "放养名称为空")
    private String fangName;
    @NotNull(message = "放养种类为空")
    @NotBlank(message = "放养种类为空")
    private String fangKind;
    @NotNull(message = "放养数量为空")
    @NotBlank(message = "放养数量为空")
    private String fangCount;
    @NotNull(message = "坐标为空")
    private Double x;
    @NotNull(message = "坐标为空")
    private Double y;
    @NotNull(message = "用户信息为空")
    private Integer userId;

}
