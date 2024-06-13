package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.SuppliesMapper;
import com.lambs.lambsmanager.pojo.Supplies;
import com.lambs.lambsmanager.service.SuppliesServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物资接口实现类
 */
@Service
public class SuppliesServerImpl implements SuppliesServer {

    //导入物资映射层
    @Resource
    private SuppliesMapper suppliesMapper;

    /**
     * 获取个人物资列表
     *
     * @param supplies
     * @return
     */
    @Override
    public List<Supplies> getSuppliesList(Supplies supplies) {
        return suppliesMapper.getSuppliesList(supplies);
    }

    /**
     * 传入物资信息删除物资
     *
     * @param supplies
     * @return
     */
    @Override
    public Integer deleteSupplies(Supplies supplies) {
        return suppliesMapper.deleteSupplies(supplies);
    }

    /**
     * 传入物资信息修改物资
     *
     * @param supplies
     * @return
     */
    @Override
    public Integer updateSupplies(Supplies supplies) {

        return suppliesMapper.updateSupplies(supplies);
    }

    /**
     * 传入物资信息新增物资
     * @param supplies
     * @return
     */
    @Override
    public Integer addSupplies(Supplies supplies) {
        //判断是否修改成功
        Integer addSupplies = suppliesMapper.addSupplies(supplies);
        if(addSupplies<=0){
            throw new MyException("新增失败");
        }
        return addSupplies;
    }
}
