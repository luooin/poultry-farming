package com.lambs.lambsmanager.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Lefter {
    private Integer id;
    private String title;
    private String path;
    private String icon;
    private Integer parent_id;
    private Integer is_leaf;
    private List<Lefter> child;
}
