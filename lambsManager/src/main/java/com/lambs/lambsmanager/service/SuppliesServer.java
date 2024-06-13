package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Supplies;

import java.util.List;

/**
 * 物资接口
 */
public interface SuppliesServer {

    /**
     * 获取个人物资列表
     * @param supplies
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
