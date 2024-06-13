package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Feeds;
import com.lambs.lambsmanager.service.FeedServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 饲料控制器
 */
@RestController
@RequestMapping("/lambs/feeds")
public class FeedsController {

    //导入服务层
    @Resource
    private FeedServer feedServer;

    /**
     * 获取饲料列表
     *
     * @param feeds
     * @return
     */
    @GetMapping
    public R<List<Feeds>> getFeedsList(@Valid Feeds feeds, BindingResult bindingResult) {
        //判断是否存在异常
        if (bindingResult.hasErrors()) {
            for (ObjectError resultAllError : bindingResult.getAllErrors()) {
                //抛出异常信息
                throw new MyException(resultAllError.getDefaultMessage());
            }
        }
        //获取饲料列表
        List<Feeds> feedsList = feedServer.getFeedsList(feeds);
        return R.success(feedsList, "获取成功");
    }

    /**
     * 传入饲料信息添加饲料
     * @param feeds
     * @param bindingResult
     * @return
     */
    @PostMapping
    public R<Integer> addFeed(@RequestBody @Valid Feeds feeds, BindingResult bindingResult) {
        //判断是否存在异常
        if (bindingResult.hasErrors()) {
            for (ObjectError resultAllError : bindingResult.getAllErrors()) {
                //抛出异常信息
                throw new MyException(resultAllError.getDefaultMessage());
            }
        }
        //添加饲料方法
        Integer addFeed = feedServer.addFeed(feeds);
        return R.success(addFeed, "添加成功");
    }

    /**
     * 传入参数删除饲料信息
     *
     * @param feeds
     * @param bindingResult
     * @return
     */
    @DeleteMapping
    public R<Integer> deleteFeed(@RequestBody @Valid Feeds feeds, BindingResult bindingResult) {
        //判断是否存在异常
        if (bindingResult.hasErrors()) {
            for (ObjectError resultAllError : bindingResult.getAllErrors()) {
                //抛出异常信息
                throw new MyException(resultAllError.getDefaultMessage());
            }
        }
        Integer deleteFeed = feedServer.deleteFeed(feeds);
        return R.success(deleteFeed, "删除成功");
    }

    /**
     * 传入饲料信息修改饲料
     *
     * @param feeds
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> updateFeed(@RequestBody @Valid Feeds feeds, BindingResult bindingResult) {
        //判断是否存在异常
        if (bindingResult.hasErrors()) {
            for (ObjectError resultAllError : bindingResult.getAllErrors()) {
                //抛出异常信息
                throw new MyException(resultAllError.getDefaultMessage());
            }
        }
        Integer updateFeed = feedServer.updateFeed(feeds);
        return R.success(updateFeed, "修改成功");
    }
}
