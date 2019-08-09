package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/***
 * @ClassName: ChapterControllter
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@RequestMapping("chapter")
@RestController
public class ChapterControllter {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("show")
    public Map<String,Object> show(String id,Integer page,Integer rows){
        Map<String, Object> map = chapterService.selectAll(id, page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper,String sid){
        String uid=null;
        if("add".equals(oper)){
            chapter.setAlbum_id(sid);
            uid = chapterService.add(chapter);
        }else if("del".equals(oper)){
            chapterService.delete(chapter.getId());
        }else if("edit".equals(oper)){
            uid = chapterService.update(chapter);
        }
        return uid;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile url, String id, HttpServletRequest request){
        HashMap<String, Object> map = chapterService.upload(url, id, request);
        return map;
    }
    //文件下载 downloadChapter
    @RequestMapping("download")
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response){
        chapterService.downloadChapter(fileName,request,response);
    }
}
