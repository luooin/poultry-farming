package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Drug;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 药品映射层
 */
@Mapper
public interface DrugMapper {

    /**
     * 获取药品列表
     *
     * @param drug
     * @return
     */
    List<Drug> getDrugList(Drug drug);

    /**
     * 传入药品信息更新药品
     * @param drug
     * @return
     */
    Integer updateDrug(Drug drug);

    /**
     * 传入药品信息删除药品
     *
     * @param drug
     * @return
     */
    Integer deleteDrug(Drug drug);

    /**
     * 新增药品
     *
     * @param drug
     * @return
     */
    Integer addDrug(Drug drug);
}
