package com.baizhi.serviceimpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/***
 * @ClassName: AdminServiceImpl
 * @Auther: 20203
 * @Date: 2019/7/30
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> login(String code, Admin admin,  HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        String code1 = (String) session.getAttribute("code");
        if(code1.equals(code)){
            Admin login = adminDao.login(admin);
            if(admin.getUsername().equals(login.getUsername())){
                if(login.getPassword().equals(admin.getPassword())){
                    session.setAttribute("Admin",login);
                    map.put("success","200");
                    map.put("message","登陆成功");
                }else{
                    map.put("success","400");
                    map.put("message","密码错误");
                }
            }else{
                map.put("success","400");
                map.put("message","用户不存在");
            }
        }else{
            map.put("success","400");
            map.put("message","验证码错误");
        }
        return map;
    }
}
