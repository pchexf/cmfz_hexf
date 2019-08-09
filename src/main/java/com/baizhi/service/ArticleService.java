package com.baizhi.service;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/***
 * @ClassName: UserService
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public interface ArticleService {
    /**
     * 查找所有
     * @param page
     * @param rows
     * @return
     */
    Map<String,Object> selectAll(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 修改
     * @param article
     */
    String update(Article article);

    /**
     * 增加
     * @param article
     */
    void add(Article article);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
}
