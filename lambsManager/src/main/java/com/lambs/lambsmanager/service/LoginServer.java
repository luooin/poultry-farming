package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.LogUser;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.pojo.Register;

import java.util.List;

/**
 * 登录接口
 */
public interface LoginServer {
    /**
     * 传入用户信息进行登录
     *
     * @param login
     * @return
     */
    List<Login> getLogin(Login login);

    /**
     * 传入注册信息,新增登录用户
     *
     * @param register
     * @return
     */
    Integer AddLoginUser(Register register);

    /**
     * 传入参数修改login
     * @param logUser
     * @return
     */
    Integer alterLogMSg(LogUser logUser);
}
