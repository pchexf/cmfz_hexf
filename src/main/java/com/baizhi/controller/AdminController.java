package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/***
 * @ClassName: AdminController
 * @Auther: 20203
 * @Date: 2019/7/30
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        String code = ImageCodeUtil.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("code",code);
        BufferedImage image = ImageCodeUtil.createImage(code);
        response.setContentType("image/pmg");
        try{
            ImageIO.write(image,"png",response.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 后台管理员登陆
     * @param admin
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(String code,Admin admin, Model model,  HttpServletRequest request){
        Map<String, Object> map = adminService.login(code,admin,request);
        return map;
    }

    /**
     * 后台管理员退出
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
