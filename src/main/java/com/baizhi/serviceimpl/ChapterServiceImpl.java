package com.baizhi.serviceimpl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/***
 * @ClassName: ChapterServiceImpl
 * @Auther: 20203
 * @Date: 2019/8/1
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Map<String,Object> selectAll(String id, Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pages=(page-1)*rows;
        List<Chapter> list =chapterDao.selectAll(id,pages,rows);
        Integer integer = chapterDao.selectPage(id);
        Integer count=integer%rows==0?integer/rows:(integer/rows+1);
        map.put("total",count);
        map.put("page",page);
        map.put("records",integer);
        map.put("rows",list);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setUp_date(new Date());
        chapterDao.add(chapter);
        return chapter.getId();
    }

    @Override
    public void delete(String id) {
        chapterDao.delete(id);
    }

    @Override
    public String update(Chapter chapter) {
        chapterDao.update(chapter);
        return chapter.getId();
    }

    @Override
    public HashMap<String, Object> upload(MultipartFile url, String id, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");

        File file = new File(realPath);

        if(!file.exists()){
            file.mkdirs();
        }
        //获取上传的文件名子
        String filename = url.getOriginalFilename();
        //给文件加上时间戳前缀
        String name=new Date().getTime()+"-"+filename;

        //文件上传
        try {
            url.transferTo(new File(realPath,name));

            //获取文件大小-1
            long size = url.getSize();
            String sizes =size/1024/1024+"MB";
            //获取文件大小-2
            DecimalFormat format = new DecimalFormat("0.00");
            String str = String.valueOf(size);
            Double dd = Double.valueOf(str)/1024/1024;
            String newsizess = format.format(dd)+"MB";

            //获取文件时长
            AudioFileIO fileIO = new AudioFileIO();
            AudioFile audio = fileIO.readFile(new File(realPath, name));
            AudioHeader audioHeader = audio.getAudioHeader();
            //获取时长    分钟
            int length = audioHeader.getTrackLength();
            String duration=length/60+"分"+length%60+"秒";

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(name);
            chapter.setSize(newsizess);
            chapter.setDuration(duration);

            chapterDao.update(chapter);

            map.put("success","200");
            map.put("message","上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success","400");
            map.put("message","上传失败");
        }

        return map;
    }

    @Override
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response) {
        //获取文件所在路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");

        //根据路径读取文件
        try {
            FileInputStream inputStream = new FileInputStream(new File(realPath, fileName));

            //设置文件响应格式   响应头   attachment:以附件的形式下载，   inline:在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"utf-8"));

            ServletOutputStream outputStream = response.getOutputStream();
            //文件下载
            IOUtils.copy(inputStream,outputStream);

            //关闭资源
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
