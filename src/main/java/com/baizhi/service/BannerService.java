package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

/***
 * @ClassName: BannerService
 * @Auther: 20203
 * @Date: 2019/7/30
 */
public interface BannerService {
    /**
     * 查询所有轮播图
     * @return
     */
    Map<String, Object> select(Integer page, Integer rows);

    /**
     * 增加轮播图
     * @param banner
     */
    String add(Banner banner);

    /**
     * 通过id删除轮播图
     * @param id
     */
    void delete(String id);

    /**
     * 通过Id查找轮播图
     * @param id
     * @return
     */
    Banner selectByOne(String id);

    /**
     * 修改轮播图
     * @param banner
     */
    String update(Banner banner);

    /**
     * 查找总页数
     * @return
     */
    Integer selectPage();

    /**
     * 修改状态
     * @return
     */
    Map<String,Object> updateStatus(String id,String status);
}
