package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Medical;

import java.util.List;

/**
 * 医疗器械接口
 */
public interface MedicalServer {

    /**
     * 传入用户信息获取医疗器械
     * @param medical
     * @return
     */
    List<Medical> getMedicalList(Medical medical);

    /**
     * 传入参数新增医疗器械
     * @param medical
     * @return
     */
    Integer addMedical(Medical medical);

    /**
     * 传入参数修改器械
     * @param medical
     * @return
     */
    Integer updateMedical(Medical medical);
}
