package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.FangMapper;
import com.lambs.lambsmanager.pojo.Fang;
import com.lambs.lambsmanager.service.FangServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 放养接口实现类
 */
@Service
public class FangServerImpl implements FangServer {

    //导入映射层
    @Resource
    private FangMapper fangMapper;

    /**
     * 传入参数添加放养信息
     *
     * @param fang
     * @return
     */
    @Override
    public Integer addFang(Fang fang) {
        Integer addFang = fangMapper.addFang(fang);
        //判断是否成功
        if (addFang <= 0) {
            throw new MyException("添加失败");
        }
        return addFang;
    }

    /**
     * 获取所有放养信息
     *
     * @return
     */
    @Override
    public List<Fang> getFang(Fang fang) {
        List<Fang> fangList = fangMapper.getFang(fang);
        //判断是否获取成功
        if (fangList.size() == 0) {
            throw new MyException("您暂时没有放养信息");
        }
        return fangList;
    }
}
