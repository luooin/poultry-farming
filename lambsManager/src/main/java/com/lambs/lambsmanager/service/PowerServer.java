package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Power;

import java.util.List;

/**
 * 权限接口
 */
public interface PowerServer {

    /**
     * 获取权限列表
     * @return
     */
    List<Power>getPowerList();
}
