package com.baizhi.serviceimpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/***
 * @ClassName: AlbumServiceImpl
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Map<String,Object> selectAll(int page, int rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pages=(page-1)*rows;
        List<Album> list = albumDao.selectAll(pages,rows);
        Integer integer = albumDao.selectPage();
        Integer count=integer%rows==0?integer/rows:(integer/rows+1);
        map.put("total",count);
        map.put("page",page);
        map.put("records",integer);
        map.put("rows",list);
        return map;
    }

    @Override
    public String add(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setPud_date(new Date());
        album.setCount(0);
        album.setScore(0.0);
        albumDao.add(album);
        return album.getId();
    }

    @Override
    public void delete(String id) {
        albumDao.delete(id);
    }

    @Override
    public String update(Album album) {
        albumDao.update(album);
        return album.getId();
    }

    @Override
    public void uplaod(MultipartFile cover_img, String id, HttpServletRequest request) {
        if(cover_img.isEmpty()){
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = cover_img.getOriginalFilename();
            String name = new Date().getTime() + "-" + filename;
            try {
                cover_img.transferTo(new File(realPath, name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Album album = new Album();
            album.setId(id);
            album.setCover_img(name);
            albumDao.update(album);
        }
    }
}
