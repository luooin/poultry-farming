package com.lambs.lambsmanager.service;

import com.lambs.lambsmanager.pojo.Feeds;

import java.util.List;

/**
 * 饲料接口
 */
public interface FeedServer {

    /**
     * 获取所有饲料信息
     * @param feeds
     * @return
     */
    List<Feeds> getFeedsList(Feeds feeds);

    /**
     * 传入参数添加饲料信息
     * @param feeds
     * @return
     */
    Integer addFeed(Feeds feeds);

    /**
     * 传入参数删除饲料信息
     * @param feeds
     * @return
     */
    Integer deleteFeed(Feeds feeds);

    /**
     * 传入饲料信息修改饲料
     * @param feeds
     * @return
     */
    Integer updateFeed(Feeds feeds);
}
