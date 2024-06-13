package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.LoginMapper;
import com.lambs.lambsmanager.pojo.LogUser;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.pojo.Register;
import com.lambs.lambsmanager.service.LoginServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录接口实现
 */
@Service
public class LoginServerImpl implements LoginServer {

    //导入映射层
    @Resource
    private LoginMapper loginMapper;

    /**
     * 传入用户信息进行登录
     *
     * @param login
     * @return
     */
    @Override
    public List<Login> getLogin(Login login) {
        //非空判断用户名、密码、手机号
        if (login.getUsername() == null || login.getPassword() == null) {
            throw new MyException("参数错误");
        }
        return loginMapper.getLogin(login);
    }

    /**
     * 传入注册信息,新增登录用户
     *
     * @param register
     * @return
     */
    @Override
    public Integer AddLoginUser(Register register) {
        return loginMapper.AddLoginUser(register);
    }

    /**
     * 传入参数修改login
     *
     * @param logUser
     * @return
     */
    @Override
    public Integer alterLogMSg(LogUser logUser) {
        return loginMapper.alterLogMSg(logUser);
    }
}
