package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.AnimalMapper;
import com.lambs.lambsmanager.pojo.Animal;
import com.lambs.lambsmanager.service.AnimalServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 畜禽接口实现类
 */
@Service
public class AnimalServerImpl implements AnimalServer {

    //导入服务类
    @Resource
    private AnimalMapper animalMapper;

    /**
     * 获取畜禽列表
     *
     * @param animal
     * @return
     */
    @Override
    public List<Animal> getAnimalList(Animal animal) {
        List<Animal> animalList = animalMapper.getAnimalList(animal);
        //非空判断
        if (animalList.size() == 0) {
            throw new MyException("您暂时没有畜禽列表");
        }
        return animalList;
    }

    /**
     * 传入畜禽信息新增畜禽
     *
     * @param animal
     * @return
     */
    @Override
    public Integer addAnimal(Animal animal) {
        Integer addAnimal = animalMapper.addAnimal(animal);
        //非空判断
        if (addAnimal <= 0) {
            throw new MyException("新增失败，出现错误");
        }
        return addAnimal;
    }

    /**
     * 传入参数修改畜禽
     *
     * @param animal
     * @return
     */
    @Override
    public Integer updateAnimal(Animal animal) {
        Integer updateAnimal = animalMapper.updateAnimal(animal);
        //非空判断是否成功
        if (updateAnimal <= 0) {
            throw new MyException("修改失败");
        }
        return null;
    }

    /**
     * 传入参数删除畜禽
     * @param animal
     * @return
     */
    @Override
    public Integer deleteAnimal(Animal animal) {
        Integer deleteAnimal = animalMapper.deleteAnimal(animal);
        //是否成功
        if(deleteAnimal<=0){
            throw new MyException("删除失败,出现错误");
        }
        return deleteAnimal;
    }
}
