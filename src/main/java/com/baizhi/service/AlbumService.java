package com.baizhi.service;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @ClassName: AlbumService
 * @Auther: 20203
 * @Date: 2019/8/1
 */
public interface AlbumService {
    /**
     * 查找所有专辑
     * @return
     * @param page
     * @param rows
     */
    Map<String,Object> selectAll(@Param("page") int page, @Param("rows") int rows);

    /**
     * 增加专辑
     * @param album
     */
    String add(Album album);

    /**
     * 删除专辑
     * @param id
     */
    void delete(String id);

    /**
     * 修改专辑
     * @param album
     */
    String update(Album album);

    /**
     * 上传图片
     * @param cover_img
     * @param id
     * @param request
     */

    void uplaod(MultipartFile cover_img, String id, HttpServletRequest request);
}
