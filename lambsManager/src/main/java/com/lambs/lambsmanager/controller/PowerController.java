package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.pojo.Power;
import com.lambs.lambsmanager.service.PowerServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lambs/power")
public class PowerController {

    //导入服务层
    @Resource
    private PowerServer powerServer;

    /**
     * 获取权限列表
     * @return
     */
    @GetMapping()
    public R<List<Power>> getPowerList(){
        List<Power> powerList = powerServer.getPowerList();
        return R.success(powerList,"获取成功");
    }
}
