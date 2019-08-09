package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @ClassName: AlbumController
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("show")
    public Map<String,Object> show(int page,int rows){
        Map<String, Object> map = albumService.selectAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(Album album,String oper){
        String uid=null;
        if("add".equals(oper)){
            uid = albumService.add(album);
        }else if("del".equals(oper)){
           albumService.delete(album.getId());
        }else if("edit".equals(oper)){
            if("".equals(album.getCover_img())){
               album.setCover_img(null);
            }
            uid = albumService.update(album);
        }
        return uid;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile cover_img, String id, HttpServletRequest request){
        albumService.uplaod(cover_img,id,request);
    }
}
