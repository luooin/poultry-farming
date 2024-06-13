package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Breed;
import com.lambs.lambsmanager.service.BreedServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lambs/breed")
public class BreedController {

    //导入服务层
    @Resource
    private BreedServer breedServer;

    /**
     * 使用用户id获取繁育列表
     *
     * @param breed
     * @param bindingResult
     * @return
     */
    @GetMapping
    public R<List<Breed>> getBreedList(@Valid Breed breed, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                //抛出自定义异常
                throw new MyException(error.getDefaultMessage());
            }
        }
        List<Breed> breedList = breedServer.getBreedList(breed);
        return R.success(breedList, "返回成功");
    }

    /**
     * 获取繁育基地信息新增繁育基地
     *
     * @return
     */
    @PostMapping
    public R<Integer> addBreed(@RequestBody @Valid Breed breed, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                //抛出自定义异常
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer addBreed = breedServer.addBreed(breed);
        return R.success(addBreed, "新增成功");
    }

    /**
     * 获取繁育信息修改繁育基地
     *
     * @param breed
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> updateBreed(@RequestBody @Valid Breed breed, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                //抛出自定义异常
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer updateBreed = breedServer.updateBreed(breed);
        return R.success(updateBreed, "修改成功");
    }

    /**
     * 传入繁育信息删除繁育基地
     *
     * @param breed
     * @param bindingResult
     * @return
     */
    @DeleteMapping
    public R<Integer> deleteBreed(@RequestBody @Valid Breed breed, BindingResult bindingResult) {
        //判断是否有错误信息
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                //抛出自定义异常
                throw new MyException(error.getDefaultMessage());
            }
        }
        Integer deleteBreed = breedServer.deleteBreed(breed);
        return R.success(deleteBreed, "删除成功");
    }

}
