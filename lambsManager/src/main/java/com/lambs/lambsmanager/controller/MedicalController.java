package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Medical;
import com.lambs.lambsmanager.service.MedicalServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lambs/medical")
public class MedicalController {

    //导入服务层
    @Resource
    private MedicalServer medicalServer;

    /**
     * 传入参数获取器械列表
     * @param medical
     * @param bindingResult
     * @return
     */
    @GetMapping
    public R<List<Medical>> getMedicalList(@Valid Medical medical, BindingResult bindingResult) {
        //判断是否有错误
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //获取器械列表
        List<Medical> medicalList = medicalServer.getMedicalList(medical);
        return R.success(medicalList, "获取成功");
    }

    /**
     * 传入参数新增医疗器械
     * @param medical
     * @param bindingResult
     * @return
     */
    @PostMapping
    public R<Integer> addMedical(@RequestBody @Valid Medical medical, BindingResult bindingResult) {
        //判断是否有错误
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //调用方法新增
        Integer addMedical = medicalServer.addMedical(medical);
        return R.success(addMedical, "新增成功");
    }

    /**
     * 传入参数修改器械
     * @param medical
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> updateMedical(@RequestBody @Valid Medical medical, BindingResult bindingResult){
        //判断是否有错误
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer updateMedical = medicalServer.updateMedical(medical);
        return R.success(updateMedical,"更新成功");
    }
}
