package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @ClassName: ArticleDao
 * @Auther: 20203
 * @Date: 2019/8/1
 */
public interface ArticleDao {
    /**
     * 查找所有
     * @param page
     * @param rows
     * @return
     */
    List<Article> selectAll(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 修改用户
     * @param article
     */
    void update(Article article);

    /**
     * 增加用户
     * @param article
     */
    void add(Article article);

    /**
     * 查找总条数
     * @return
     */
    Integer selectPage();

    /**
     * 删除
     * @param id
     */
    void delete(String id);
}
