package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @ClassName: AlbumDao
 * @Auther: 20203
 * @Date: 2019/7/31
 */
public interface AlbumDao {
    /**
     * 查找所有专辑
     * @return
     * @param page
     * @param rows
     */
    List<Album> selectAll(@Param("page") int page, @Param("rows") int rows);

    /**
     * 增加专辑
     * @param album
     */
    void add(Album album);

    /**
     * 删除专辑
     * @param id
     */
    void delete(String id);

    /**
     * 修改专辑
     * @param album
     */
    void update(Album album);

    /**
     * 查找总条数
     * @return
     */
    Integer selectPage();
}
