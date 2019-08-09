package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @ClassName: AdminService
 * @Auther: 20203
 * @Date: 2019/7/30
 */
public interface AdminService {
    Map<String,Object> login(String code, Admin admin, HttpServletRequest request);
}
