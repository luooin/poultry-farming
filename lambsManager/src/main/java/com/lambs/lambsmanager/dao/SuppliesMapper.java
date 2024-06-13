package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Supplies;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 物资映射层
 */
@Mapper
public interface SuppliesMapper {

    /**
     * 获取个人物资列表
     * @return
     */
    List<Supplies> getSuppliesList(Supplies supplies);

    /**
     * 传入物资信息删除物资
     * @param supplies
     * @return
     */
    Integer deleteSupplies(Supplies supplies);

    /**
     * 传入物资信息修改物资
     * @param supplies
     * @return
     */
    Integer updateSupplies(Supplies supplies);

    /**
     * 传入物资信息新增物资
     * @param supplies
     * @return
     */
    Integer addSupplies(Supplies supplies);
}
