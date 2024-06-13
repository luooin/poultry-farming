package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Lefter;

import java.util.List;

/**
 * 左侧导航栏接口
 */
public interface LeftServer {
    /**
     * 获取左侧导航栏
     * @return
     */
    List<Lefter> getLeft();
}
