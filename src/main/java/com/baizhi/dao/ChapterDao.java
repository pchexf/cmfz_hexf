package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @ClassName: ChapterDao
 * @Auther: 20203
 * @Date: 2019/7/31
 */
public interface ChapterDao {
    /**
     * 查找某专辑下所有章节
     * @return
     */
    List<Chapter> selectAll(@Param("id") String id, @Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 增加章节
     * @param chapter
     */
    void add(Chapter chapter);

    /**
     * 删除章节
     * @param id
     */
    void delete(String id);

    /**
     * 修改章节
     * @param chapter
     */
    void update(Chapter chapter);

    /**
     * 查找某专辑下所有章节的条数
     * @param id
     * @return
     */
    Integer selectPage(String id);
}
