package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Supplies;
import com.lambs.lambsmanager.service.SuppliesServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物资控制器
 */
@RestController
@RequestMapping("/lambs/supplies")
public class SuppliesController {

    //导入物资接口
    @Resource
    private SuppliesServer suppliesServer;

    /**
     * 获取个人物资列表
     *
     * @return
     */
    @GetMapping
    public R<List<Supplies>> getSuppliesList(@Valid Supplies supplies, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        List<Supplies> suppliesList = suppliesServer.getSuppliesList(supplies);
        return R.success(suppliesList, "获取成功");
    }

    /**
     * 传入物资信息删除物资
     * @param supplies
     * @param bindingResult
     * @return
     */
    @DeleteMapping
    public R<Integer> deleteSupplies(@RequestBody @Valid Supplies supplies, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer deleteSupplies = suppliesServer.deleteSupplies(supplies);
        return R.success(deleteSupplies,"删除成功");
    }

    /**
     * 传入物资信息修改物资
     * @param supplies
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> updateSupplies(@RequestBody @Valid Supplies supplies, BindingResult bindingResult){
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer updateSupplies = suppliesServer.updateSupplies(supplies);
        //判断是否修改成功
        if(updateSupplies<=0){
            throw new MyException("修改失败");
        }
        return R.success(updateSupplies,"修改成功");
    }

    /**
     * 传入物资信息新增物资
     * @param supplies
     * @param bindingResult
     * @return
     */
    @PostMapping
    public R<Integer> addSupplies(@RequestBody @Valid Supplies supplies, BindingResult bindingResult){
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer addSupplies = suppliesServer.addSupplies(supplies);
        return R.success(addSupplies,"新增成功");
    }
}
