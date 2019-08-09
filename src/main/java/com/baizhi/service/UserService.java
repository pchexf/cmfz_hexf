package com.baizhi.service;

import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

/***
 * @ClassName: UserService
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public interface UserService {
    /**
     * 查找所有
     *
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> selectAll(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 修改用户
     *
     * @param user
     */
    String update(User user);

    /**
     * 增加用户
     *
     * @param user
     */
    void add(User user);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    Map<String, Object> updateStatus(String id, String status);

    /**
     * 导出
     */
    void esport();

    /**
     * 查询城市
     *
     * @return
     */
    ArrayList<Pro> selectCity();

    /**
     * 查找每月静态
     *
     * @return
     */
    Map<String, Object> selectConnt();

    /**
     * 查找每月动态
     *
     * @return
     */
    void select(User user);
}