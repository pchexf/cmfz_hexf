package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @ClassName: BannerDao
 * @Auther: 20203
 * @Date: 2019/7/30
 */
public interface BannerDao {
    /**
     * 查询所有轮播图
     * @return
     */
    List<Banner> select(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 增加轮播图
     * @param banner
     */
    void add(Banner banner);

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
    void update(Banner banner);

    /**
     * 查找总页数
     * @return
     */
    Integer selectPage();
}
