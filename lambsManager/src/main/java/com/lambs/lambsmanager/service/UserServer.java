package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.LogUser;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.pojo.User;

import java.util.List;

public interface UserServer {

    /**
     * 通过传入手机号添加用户
     *
     * @param phone
     * @return
     */
    Integer AddUser(String phone);

    /**
     * 查询所有用户的所有信息
     *
     * @return
     */
    List<User> getUserList();

    /**
     * 传入用户信息查询用户
     *
     * @param user
     * @return
     */
    List<User> getUserListByUser(User user);


    /**
     * 传入用户信息修改用户状态
     *
     * @param login
     * @return
     */
    Integer alterUserStatus(Login login);

    /**
     * 传入参数修改用户user和login
     *
     * @param logUser
     * @return
     */
    Integer alterUserMsg(LogUser logUser);
}
