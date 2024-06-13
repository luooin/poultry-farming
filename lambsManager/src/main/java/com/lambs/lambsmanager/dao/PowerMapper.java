package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Power;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PowerMapper {

    /**
     * 获取权限列表
     * @return
     */
    List<Power> getPowerList();
}
