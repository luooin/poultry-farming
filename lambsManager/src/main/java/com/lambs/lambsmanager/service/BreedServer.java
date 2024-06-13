package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Breed;

import java.util.List;

/**
 * 繁育接口
 */
public interface BreedServer {

    /**
     * 查询所有的繁育信息
     *
     * @param breed
     * @return
     */
    List<Breed> getBreedList(Breed breed);

    /**
     * 获取繁育信息新增繁育基地
     *
     * @param breed
     * @return
     */
    Integer addBreed(Breed breed);

    /**
     * 获取繁育信息修改繁育基地
     * @param breed
     * @return
     */
    Integer updateBreed(Breed breed);

    /**
     * 传入繁育信息删除繁育基地
     * @param breed
     * @return
     */
    Integer deleteBreed(Breed breed);
}
