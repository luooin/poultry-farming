package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Animal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 畜禽映射层
 */
@Mapper
public interface AnimalMapper {

    /**
     * 获取畜禽列表
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
