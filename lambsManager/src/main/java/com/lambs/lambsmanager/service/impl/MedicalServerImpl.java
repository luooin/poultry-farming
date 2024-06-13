package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.MedicalMapper;
import com.lambs.lambsmanager.pojo.Medical;
import com.lambs.lambsmanager.service.MedicalServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医疗器械接口实现类
 */
@Service
public class MedicalServerImpl implements MedicalServer {

    //导入映射层
    @Resource
    private MedicalMapper medicalMapper;

    /**
     * 传入用户信息获取医疗器械
     *
     * @param medical
     * @return
     */
    @Override
    public List<Medical> getMedicalList(Medical medical) {
        List<Medical> medicalList = medicalMapper.getMedicalList(medical);
        //判断是否获取到内容
        if (medicalList.size() == 0) {
            throw new MyException("暂时获取不到医疗器械");
        }
        return medicalList;
    }
    /**
     * 传入参数新增医疗器械
     *
     * @param medical
     * @return
     */
    @Override
    public Integer addMedical(Medical medical) {
        Integer addMedical = medicalMapper.addMedical(medical);
        //判断是否成功
        if (addMedical <= 0) {
            throw new MyException("新增失败");
        }
        return addMedical;
    }

    /**
     * 传入参数修改器械
     *
     * @param medical
     * @return
     */
    @Override
    public Integer updateMedical(Medical medical) {
        Integer updateMedical = medicalMapper.updateMedical(medical);
        //是否成功
        if(updateMedical<=0){
            throw new MyException("修改失败,出现错误");
        }
        return updateMedical;
    }
}
