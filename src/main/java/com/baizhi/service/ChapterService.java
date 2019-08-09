package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/***
 * @ClassName: ChapterService
 * @Auther: 20203
 * @Date: 2019/8/1
 */
public interface ChapterService {
    /**
     * 查找某专辑下所有章节
     * @return
     */
    Map<String,Object> selectAll(@Param("id") String id, @Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 增加章节
     * @param chapter
     */
    String add(Chapter chapter);

    /**
     * 删除章节
     * @param id
     */
    void delete(String id);

    /**
     * 修改章节
     * @param chapter
     */
    String update(Chapter chapter);

    /**
     * 下载
     * @param url
     * @param id
     * @param request
     * @return
     */
    HashMap<String,Object> upload(MultipartFile url, String id, HttpServletRequest request);

    /**
     * 上传
     * @param fileName
     * @param request
     * @param response
     */
    void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response);
}
