package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Register;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 注册信息映射层
 */
@Mapper
public interface RegisterMapper {
    /**
     * 传入参数则按照条件查询,没有则查询所有(用于登录界面的注册和系统管理的用户注册)
     *
     * @param register
     * @return
     */
    List<Register> getRegisterList(Register register);

    /**
     * 传入参数修改注册列表的状态
     *
     * @param register
     * @return
     */
    Integer AlterRegister(Register register);

    /**
     * 传入参数申请注册
     * @param register
     * @return
     */
    Integer ApplyRegister(Register register);
}
