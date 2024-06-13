package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.BreedMapper;
import com.lambs.lambsmanager.pojo.Breed;
import com.lambs.lambsmanager.service.BreedServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 繁育接口实现类
 */
@Service
public class BreedServerImpl implements BreedServer {

    //导入映射层
    @Resource
    private BreedMapper breedMapper;

    /**
     * 查询所有的繁育信息
     *
     * @param breed
     * @return
     */
    @Override
    public List<Breed> getBreedList(Breed breed) {
        List<Breed> breedList = breedMapper.getBreedList(breed);
        //判断是否成功
        if (breedList.size() == 0) {
            throw new MyException("繁育列表为空");
        }
        return breedList;
    }

    /**
     * 获取繁育信息新增繁育基地
     *
     * @param breed
     * @return
     */
    @Override
    public Integer addBreed(Breed breed) {
        Integer addBreed = breedMapper.addBreed(breed);
        //判断是否新增成功
        if (addBreed <= 0) {
            throw new MyException("新增失败,出现问题");
        }
        return addBreed;
    }

    /**
     * 获取繁育信息修改繁育基地
     *
     * @param breed
     * @return
     */
    @Override
    public Integer updateBreed(Breed breed) {
        Integer updateBreed = breedMapper.updateBreed(breed);
        //判断是否修改成功
        if (updateBreed <= 0) {
            throw new MyException("修改失败,服务器错误");
        }
        return updateBreed;
    }

    /**
     * 传入繁育信息删除繁育基地
     *
     * @param breed
     * @return
     */
    @Override
    public Integer deleteBreed(Breed breed) {
        //非空判断
        if (breed.getUserId() == 0 && breed.getId() == 0) {
            throw new MyException("参数错误");
        }
        Integer deleteBreed = breedMapper.deleteBreed(breed);
        //判断是否成功
        if (deleteBreed <= 0) {
            throw new MyException("删除失败,服务器错误");
        }
        return deleteBreed;
    }
}
