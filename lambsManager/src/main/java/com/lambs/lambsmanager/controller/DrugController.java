package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Drug;
import com.lambs.lambsmanager.service.DrugServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lambs/drug")
public class DrugController {

    //导入服务层
    @Resource
    private DrugServer drugServer;

    /**
     * 传入药品用户信息获取药品
     *
     * @param drug
     * @param bindingResult
     * @return
     */
    @GetMapping
    public R<List<Drug>> getDrugList(@Valid Drug drug, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //调用方法
        List<Drug> drugList = drugServer.getDrugList(drug);
        return R.success(drugList, "获取成功");
    }

    /**
     * 传入参数修改药品
     *
     * @param drug
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> UpdateDrug(@RequestBody @Valid Drug drug, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer updateDrug = drugServer.updateDrug(drug);
        return R.success(updateDrug, "修改成功");
    }

    /**
     * 传入药品参数删除药品
     *
     * @param drug
     * @param bindingResult
     * @return
     */
    @DeleteMapping
    public R<Integer> deleteDrug(@RequestBody @Valid Drug drug, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer deleteDrug = drugServer.deleteDrug(drug);
        return R.success(deleteDrug, "删除成功");
    }

    @PostMapping
    public R<Integer> addDrug(@RequestBody @Valid Drug drug, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer addDrug = drugServer.addDrug(drug);
        return R.success(addDrug, "新增成功");
    }

}
