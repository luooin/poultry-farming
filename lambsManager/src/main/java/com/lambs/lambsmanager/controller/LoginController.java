package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.JwtConfig;
import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Login;
import com.lambs.lambsmanager.service.LoginServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/lambs/login")
public class LoginController {

    //导入服务层
    @Resource
    private LoginServer loginServer;

    //导入jwt令牌
    @Resource
    private JwtConfig jwtConfig;

    /**
     * 登录功能
     *
     * @param login
     * @return
     */
    @PostMapping
    public R<Login> login(@RequestBody @Valid Login login, BindingResult bindingResult) {
        //传入用户名、手机号、密码
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //没有错误信息,调用服务层方法,传入相应的参数
        List<Login> logins = loginServer.getLogin(login);
        //返回集合非空判断
        if (logins.size() > 0) {
            Login log = logins.get(0);
            //采用id创建token,并且临时加密,防止得到真实的id
            String token = jwtConfig.createToken(log.getId() + "abc");
            //设置token
            log.setToken(token);
            return R.success(log, "登录成功");
        }
        return R.error("登录失败,请检查信息是否有误");
    }
}
