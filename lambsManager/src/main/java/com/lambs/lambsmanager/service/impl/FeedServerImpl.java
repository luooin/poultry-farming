package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.FeedsMapper;
import com.lambs.lambsmanager.pojo.Feeds;
import com.lambs.lambsmanager.service.FeedServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServerImpl implements FeedServer {

    //导入饲料映射层
    @Resource
    private FeedsMapper feedsMapper;

    /**
     * 获取所有饲料信息
     *
     * @param feeds
     * @return
     */
    @Override
    public List<Feeds> getFeedsList(Feeds feeds) {
        List<Feeds> feedsList = feedsMapper.getFeedsList(feeds);
        //非空判断
        if (feedsList.size() == 0) {
            throw new MyException("您暂时没有饲料信息");
        }
        return feedsList;
    }

    /**
     * 传入参数添加饲料信息
     *
     * @param feeds
     * @return
     */
    @Override
    public Integer addFeed(Feeds feeds) {
        Integer addFeed = feedsMapper.addFeed(feeds);
        //判断是否成功
        if (addFeed <= 0) {
            throw new MyException("添加失败,出现错误");
        }
        return addFeed;
    }

    /**
     * 传入参数删除饲料信息
     *
     * @param feeds
     * @return
     */
    @Override
    public Integer deleteFeed(Feeds feeds) {
        Integer deleteFeed = feedsMapper.deleteFeed(feeds);
        //判断是否删除成功
        if (deleteFeed <= 0) {
            throw new MyException("删除失败");
        }
        return deleteFeed;
    }

    /**
     * 传入饲料信息修改饲料
     *
     * @param feeds
     * @return
     */
    @Override
    public Integer updateFeed(Feeds feeds) {
        Integer updateFeed = feedsMapper.updateFeed(feeds);
        //判断是否成功
        if (updateFeed <= 0) {
            throw new MyException("修改失败");
        }
        return updateFeed;
    }
}
