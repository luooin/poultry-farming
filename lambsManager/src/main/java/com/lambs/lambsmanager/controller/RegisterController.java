package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Register;
import com.lambs.lambsmanager.service.RegisterServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 注册控制器
 */
@RestController
@RequestMapping("/lambs/register")
public class RegisterController {

    //导入服务层
    @Resource
    private RegisterServer registerServer;

    /**
     * 按照传入的参数查询注册表
     *
     * @param register
     * @return
     */
    @GetMapping
    public R<List<Register>> getRegister(@Valid Register register, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        List<Register> registerList = registerServer.getRegisterList(register);
        return R.success(registerList, "查询成功");
    }

    /**
     * 不带参数查询所有注册
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<Register>> getRegisterList() {
        List<Register> registerList = registerServer.getRegisterList(new Register());
        return R.success(registerList, "查询成功");
    }

    /**
     * 后端同意注册接口
     *
     * @param register
     * @return
     */
    @PostMapping("/agree")
    public R<Integer> agreeRegister(@RequestBody @Valid Register register, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //调用方法添加信息
        Integer agreeRegister = registerServer.AgreeRegister(register);
        return R.success(agreeRegister, "注册成功");
    }

    /**
     * 后端拒绝注册
     *
     * @param register
     * @return
     */
    @PutMapping
    public R<Integer> rejectRegister(@RequestBody @Valid Register register, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //调用方法获取返回结果
        Integer integer = registerServer.regectRegister(register);
        return R.success(integer, "拒绝成功");
    }

    /**
     * 申请注册
     *
     * @param register
     * @return
     */
    @PostMapping("/apply")
    public R<Integer> applyRegister(@RequestBody Register register) {
        //设置日期
        register.setDate(new Date());
        //调用方法申请注册
        Integer apply = registerServer.ApplyRegister(register);
        return R.success(apply, "申请注册成功,等待审核");
    }
}
