package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.UserMapper;
import com.lambs.lambsmanager.pojo.LogUser;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.pojo.User;
import com.lambs.lambsmanager.service.LoginServer;
import com.lambs.lambsmanager.service.UserServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServerImpl implements UserServer {

    //导入用户映射层
    @Resource
    private UserMapper userMapper;

    //导入登录映射层
    @Resource
    private LoginServer loginServer;

    /**
     * 通过传入手机号添加用户
     *
     * @param phone
     * @return
     */
    @Override
    public Integer AddUser(String phone) {
        return userMapper.AddUser(phone);
    }

    /**
     * 查询所有用户的所有信息
     *
     * @return
     */
    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    /**
     * 传入用户信息查询用户
     *
     * @param user
     * @return
     */
    @Override
    public List<User> getUserListByUser(User user) {
        return userMapper.getUserListByUser(user);
    }


    /**
     * 传入用户信息修改用户状态
     *
     * @param login
     * @return
     */
    @Override
    public Integer alterUserStatus(Login login) {
        Integer alterUser = userMapper.alterUserStatus(login);
        //判断是否修改成功
        if (alterUser <= 0) {
            throw new MyException("修改失败");
        }
        return alterUser;
    }

    /**
     * 传入参数修改用户user和login
     *
     * @param logUser
     * @return
     */
    @Transactional
    @Override
    public Integer alterUserMsg(LogUser logUser) {
        Integer alterLogMSg = loginServer.alterLogMSg(logUser);
        Integer alterUserMsg = userMapper.alterUserMsg(logUser);
        if(alterLogMSg<=0){
            throw new MyException("修改登录信息失败");
        }
        if(alterUserMsg<=0){
            throw new MyException("修改用户信息失败");
        }
        return alterUserMsg;
    }
}
