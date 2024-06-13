package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.LogUser;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.pojo.User;
import com.lambs.lambsmanager.service.UserServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/lambs/user")
public class UserController {

    //导入用户服务
    @Resource
    private UserServer userServer;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping
    public R<List<User>> getUserList() {
        //查询所有用户信息
        List<User> userList = userServer.getUserList();
        return R.success(userList);
    }

    /**
     * 修改用户状态
     *
     * @param login
     * @return
     */
    @PutMapping
    public R<Integer> alterUserStatus(@RequestBody Login login) {
        Integer alterUser = userServer.alterUserStatus(login);
        return R.success(alterUser, "修改成功");
    }

    /**
     * 传入参数修改用户user和login
     *
     * @param logUser
     * @return
     */
    @PutMapping("/upusemsg")
    public R<Integer> alterUserMsg(@RequestBody @Valid LogUser logUser, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer integer = userServer.alterUserMsg(logUser);
        return R.success(integer, "修改成功");
    }
}
