package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.pojo.Animal;
import com.lambs.lambsmanager.service.AnimalServer;
import com.lambs.lambsmanager.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lambs/animal")
public class AnimalController {

    //导入服务类
    @Resource
    private AnimalServer animalServer;

    /**
     * 传入用户信息获取畜禽列表
     *
     * @param animal
     * @param bindingResult
     * @return
     */
    @GetMapping
    public R<List<Animal>> getAnimalList(@Valid Animal animal, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        List<Animal> animalList = animalServer.getAnimalList(animal);
        return R.success(animalList, "获取成功");
    }

    /**
     * 传入畜禽信息新增畜禽
     *
     * @param animal
     * @param bindingResult
     * @return
     */
    @PostMapping
    public R<Integer> addAnimal(@RequestBody @Valid Animal animal, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //调用方法新增畜禽
        Integer addAnimal = animalServer.addAnimal(animal);
        return R.success(addAnimal, "新增成功");
    }

    /**
     * 传入参数修改畜禽
     *
     * @param animal
     * @param bindingResult
     * @return
     */
    @PutMapping
    public R<Integer> updateAnimal(@RequestBody @Valid Animal animal, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //传入参数修改畜禽
        Integer updateAnimal = animalServer.updateAnimal(animal);
        return R.success(updateAnimal, "修改成功");
    }

    /**
     * 传入参数删除畜禽
     *
     * @param animal
     * @param bindingResult
     * @return
     */
    @DeleteMapping
    public R<Integer> deleteAnimal(@RequestBody @Valid Animal animal, BindingResult bindingResult) {
        //校验
        if (bindingResult.hasErrors()) {
            //获取所有错误,并且遍历,抛出自定义错误
            for (ObjectError error : bindingResult.getAllErrors()) {
                throw new MyException(error.getDefaultMessage());
            }
        }
        //传入畜禽删除畜禽
        Integer deleteAnimal = animalServer.deleteAnimal(animal);
        return R.success(deleteAnimal, "删除成功");
    }
}
