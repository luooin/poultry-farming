package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Fang;
import com.lambs.lambsmanager.service.FangServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lambs/fang")
public class FangController {

    //导入放养接口
    @Resource
    private FangServer fangServer;

    /**
     * 传入参数添加放养信息
     *
     * @param fang
     * @return
     */
    @PostMapping
    public R<Integer> addFang(@RequestBody @Valid Fang fang, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer addFang = fangServer.addFang(fang);
        return R.success(addFang, "添加成功");
    }

    /**
     * 传入放养信息获取放养数据
     *
     * @param fang
     * @param
     * @return
     */
    @GetMapping
    public R<List<Fang>> getFang(Fang fang) {
        List<Fang> fangList = fangServer.getFang(fang);
        return R.success(fangList, "获取信息成功");
    }
}
