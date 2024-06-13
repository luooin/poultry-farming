package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.dao.PowerMapper;
import com.lambs.lambsmanager.pojo.Power;
import com.lambs.lambsmanager.service.PowerServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限接口实现类
 */
@Service
public class PowerServerImpl implements PowerServer {

    //导入映射层
    @Resource
    private PowerMapper powerMapper;

    /**
     * 获取权限列表
     * @return
     */
    @Override
    public List<Power> getPowerList() {
        return powerMapper.getPowerList();
    }
}
