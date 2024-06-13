package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Fang;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 放养映射层
 */
@Mapper
public interface FangMapper {

    /**
     * 传入参数添加放养信息
     *
     * @param fang
     * @return
     */
    Integer addFang(Fang fang);

    /**
     * 获取所有放养信息
     *
     * @return
     */
    List<Fang> getFang(Fang fang);
}
