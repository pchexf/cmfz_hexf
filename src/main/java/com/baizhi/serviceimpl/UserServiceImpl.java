package com.baizhi.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/***
 * @ClassName: UserServiceImpl
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> selectAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pages = (page - 1) * rows;
        Integer integer = userDao.selectPage();
        Integer totle=integer%rows==0?integer/rows:integer/rows+1;
        List<User> users = userDao.selectAll(pages, rows);
        map.put("total",totle);
        map.put("page",page);
        map.put("records",integer);
        map.put("rows",users);
        return map;
    }

    @Override
    public String update(User user) {
        userDao.update(user);
        return user.getId();
    }

    @Override
    public void add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setReg_date(new Date());
        userDao.add(user);
    }

    @Override
    public Map<String, Object> updateStatus(String id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        HashMap<String, Object> map = new HashMap<>();
        try {
            userDao.update(user);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public void esport() {
        List<User> list = userDao.selectAll(0, userDao.selectPage());
        for (User user : list) {
            user.setPic_img("src/main/webapp/upload/photo/"+user.getPic_img());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户","信息表"), User .class, list);
        try {
            FileOutputStream stream = new FileOutputStream(new File("F://test1.xls"));
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public ArrayList<Pro> selectCity() {
        ArrayList<Pro> list = new ArrayList<>();
        Pro pro = new Pro("男", userDao.select("男"));
        Pro pro1 = new Pro("女", userDao.select("女"));
        list.add(pro1);
        list.add(pro);
        return list;
    }

    @Override
    public Map<String, Object> selectConnt() {
        Map<String, Object> map = new HashMap<>();
        List list1 = userDao.selectMonth("男");
        List list2 = userDao.selectMonth("女");
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月"));
        map.put("boys", list1);
        map.put("girls", list2);
        return map;
    }

    @Override
    public void select(User user) {
        //增加方法
        add(user);
        //查找每月动态
        Map<String, Object> map = selectConnt();
        //转化JSON
        String content = JSON.toJSONString(map);
        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-2a34cc77845e4d1c97858dca6dbea0df");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("myChannel", content);

    }

}
