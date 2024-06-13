package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Fang;

import java.util.List;

public interface FangServer {

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
