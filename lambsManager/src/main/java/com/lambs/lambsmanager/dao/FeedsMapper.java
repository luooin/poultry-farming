package com.lambs.lambsmanager.dao;

import com.lambs.lambsmanager.pojo.Feeds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 饲料管理映射层
 */
@Mapper
public interface FeedsMapper {

    /**
     * 传入用户id获取饲料列表
     * @param feeds
     * @return
     */
    List<Feeds>getFeedsList(Feeds feeds);

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
