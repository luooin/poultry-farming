package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.dao.LeftMapper;
import com.lambs.lambsmanager.pojo.Lefter;
import com.lambs.lambsmanager.service.LeftServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 左侧导航栏接口实现
 */
@Service
public class LeftServerImpl implements LeftServer {
    //导入左侧导航栏映射层
    @Resource
    private LeftMapper leftMapper;

    /**
     * 获取左侧导航栏
     *
     * @return
     */
    @Override
    public List<Lefter> getLeft() {
        return leftMapper.getLeft();
    }
}
