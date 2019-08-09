package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @ClassName: UserDao
 * @Auther: 20203
 * @Date: 2019/8/1
 */
public interface UserDao {
    /**
     * 查找所有
     * @param page
     * @param rows
     * @return
     */
    List<User> selectAll(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 修改用户
     * @param user
     */
    void update(User user);

    /**
     * 增加用户
     * @param user
     */
    void add(User user);

    /**
     * 查找总条数
     * @return
     */
    Integer selectPage();

    /**
     * 查找城市
     * @return
     */
    List<City> select(String sex);

    /**
     * 查找每月
     * @param sex
     * @return
     */
    List selectMonth(String sex);

}
