package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Lefter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 左侧导航栏映射层
 */
@Mapper
public interface LeftMapper {
    List<Lefter> getLeft();
}
