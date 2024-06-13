package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.pojo.Lefter;
import com.lambs.lambsmanager.service.LeftServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 左侧导航栏
 */
@RestController
@RequestMapping("/lambs/left")
public class LeftController {

    @Resource
    private LeftServer leftServer;

    /**
     * 获取左侧导航栏
     *
     * @return
     */
    @GetMapping
    public R<List<Lefter>> getLeft() {
        try {
            List<Lefter> left = leftServer.getLeft();
            return R.success(left, "获取信息成功");
        } catch (Exception e) {
            return R.error("获取信息失败,请重试");
        }
    }
}
