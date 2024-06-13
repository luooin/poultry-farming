package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.DrugMapper;
import com.lambs.lambsmanager.pojo.Drug;
import com.lambs.lambsmanager.service.DrugServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品接口实现类
 */
@Service
public class DrugServerImpl implements DrugServer {

    //导入映射层
    @Resource
    private DrugMapper drugMapper;

    /**
     * 获取药品列表
     *
     * @param drug
     * @return
     */
    @Override
    public List<Drug> getDrugList(Drug drug) {
        List<Drug> drugList = drugMapper.getDrugList(drug);
        //非空判断
        if (drugList.size() == 0) {
            throw new MyException("您的仓库暂时没有药品");
        }
        return drugList;
    }

    /**
     * 传入药品信息更新药品
     *
     * @param drug
     * @return
     */
    @Override
    public Integer updateDrug(Drug drug) {
        Integer updateDrug = drugMapper.updateDrug(drug);
        //判断是否成功
        if (updateDrug <= 0) {
            throw new MyException("更新失败,出现错误");
        }
        return updateDrug;
    }

    /**
     * 传入药品信息删除药品
     *
     * @param drug
     * @return
     */
    @Override
    public Integer deleteDrug(Drug drug) {
        //判断id非空
        if (drug.getId() <= 0) {
            throw new MyException("参数有误");
        }
        Integer deleteDrug = drugMapper.deleteDrug(drug);
        //判断是否删除成功
        if (deleteDrug <= 0) {
            throw new MyException("删除错误,出现问题");
        }
        return deleteDrug;
    }

    /**
     * 传入参数新增药品
     *
     * @param drug
     * @return
     */
    @Override
    public Integer addDrug(Drug drug) {
        Integer addDrug = drugMapper.addDrug(drug);
        //判断是否成功
        if (addDrug <= 0) {
            throw new MyException("新增错误,出现问题");
        }
        return addDrug;
    }
}
