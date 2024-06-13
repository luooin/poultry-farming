package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Register;

import java.util.List;

/**
 * 注册接口
 */
public interface RegisterServer {
    /**
     * 传入参数则按照条件查询,没有则查询所有(用于登录界面的注册和系统管理的用户注册)
     *
     * @param register
     * @return
     */
    List<Register> getRegisterList(Register register);

    /**
     * 传入参数，同意注册
     *
     * @param register
     * @return
     */
    Integer AgreeRegister(Register register);

    /**
     * 传入参数,拒绝注册
     *
     * @param register
     * @return
     */
    Integer regectRegister(Register register);

    /**
     * 传入参数申请注册
     * @param register
     * @return
     */
    Integer ApplyRegister(Register register);
}
