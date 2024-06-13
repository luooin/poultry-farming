package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Animal;

import java.util.List;

/**
 * 畜禽接口
 */
public interface AnimalServer {

    /**
     * 获取畜禽列表
     *
     * @param animal
     * @return
     */
    List<Animal> getAnimalList(Animal animal);

    /**
     * 传入畜禽信息新增畜禽
     *
     * @param animal
     * @return
     */
    Integer addAnimal(Animal animal);

    /**
     * 传入参数修改畜禽
     *
     * @param animal
     * @return
     */
    Integer updateAnimal(Animal animal);

    /**
     * 传入参数删除畜禽
     * @param animal
     * @return
     */
    Integer deleteAnimal(Animal animal);
}
