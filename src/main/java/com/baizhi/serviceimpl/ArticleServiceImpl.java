package com.baizhi.serviceimpl;


import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 * @ClassName:
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pages=(page-1)*rows;
        List<Article> list = articleDao.selectAll(pages,rows);
        Integer integer = articleDao.selectPage();
        Integer count=integer%rows==0?integer/rows:(integer/rows+1);
        map.put("total",count);
        map.put("page",page);
        map.put("records",integer);
        map.put("rows",list);
        return map;
    }

    @Override
    public String update(Article article) {
        articleDao.update(article);
        return article.getId();
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setUp_date(new Date());
        article.setStatus("1");
        articleDao.add(article);
    }

    @Override
    public void delete(String id) {
        articleDao.delete(id);
    }
}
