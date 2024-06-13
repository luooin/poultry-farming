package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Medical;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医疗器械映射层
 */
@Mapper
public interface MedicalMapper {
    /**
     * 传入用户信息获取医疗器械
     *
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
