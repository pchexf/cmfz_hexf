package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/***
 * @ClassName: BannerController
 * @Auther: 20203
 * @Date: 2019/7/30
 */
@RequestMapping("banner")
@RestController
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("show")
    public Map<String,Object> show(Integer page,Integer rows){
        Map<String, Object> map = bannerService.select(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(Banner banner,String oper){
        String uid=null;
        if("add".equals(oper)){
            uid = bannerService.add(banner);
        }else if("del".equals(oper)){
            bannerService.delete(banner.getId());
        }else if("edit".equals(oper)){
            if("".equals(banner.getImg_path())){
                banner.setImg_path(null);
            }
            uid = bannerService.update(banner);
        }
        return uid;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile img_path, String id, HttpServletRequest request){
        Banner banner = new Banner();
        banner.setId(id);
        if(img_path.isEmpty()) {
        }else{
            //1.获取要上传文件夹的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

            //获取文件夹
            File file = new File(realPath);

            //创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

            //获取上传文件的名字
            String filename = img_path.getOriginalFilename();

            String name = new Date().getTime() + "-" + filename;

            //文件上传
            try {
                img_path.transferTo(new File(realPath, name));
            } catch (IOException e) {
                e.printStackTrace();
            }


            banner.setImg_path(name);
            //做修改
            bannerService.update(banner);
        }
    }
    @RequestMapping("updateStatus")
    public Map<String,Object> updateStatus(String id,String status){
        Map<String, Object> map = bannerService.updateStatus(id, status);
        return map;
    }
}
