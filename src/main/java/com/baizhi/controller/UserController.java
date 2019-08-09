package com.baizhi.controller;

import com.baizhi.entity.Pro;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

/***
 * @ClassName: UserController
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("show")
    public Map<String,Object> show(Integer page,Integer rows){
        Map<String, Object> map = userService.selectAll(page, rows);
        return map;
    }
    @RequestMapping("updateStatus")
    public Map<String,Object> updateStatus(String id,String status){
        Map<String, Object> map = userService.updateStatus(id,status);
        return map;
    }
    @RequestMapping("esport")
    public void esport(){
        userService.esport();
    }
    @RequestMapping("selectCity")
    public ArrayList<Pro> selectCity(){
        ArrayList<Pro> pros = userService.selectCity();
        return pros;
    }
    @RequestMapping("selectMonth")
    public Map<String, Object> selectMonth(){
        Map<String, Object> map = userService.selectConnt();
        return map;
    }
}
