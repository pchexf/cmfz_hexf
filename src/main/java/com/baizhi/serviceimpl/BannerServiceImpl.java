package com.baizhi.serviceimpl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 * @ClassName: BannerServiceImpl
 * @Auther: 20203
 * @Date: 2019/7/30
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    /**
     * 查询所有轮播图
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> select(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pages=(page-1)*rows;
        List<Banner> list = bannerDao.select(pages,rows);
        Integer integer = bannerDao.selectPage();
        Integer count=integer%rows==0?integer/rows:(integer/rows+1);
        map.put("total",count);
        map.put("page",page);
        map.put("records",integer);
        map.put("rows",list);
        return map;
    }
    /**
     * 增加轮播图
     * @param banner
     */
    @Override
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setUp_date(new Date());
        banner.setStatus("1");
        bannerDao.add(banner);
        return banner.getId();
    }
    /**
     * 通过id删除轮播图
     * @param id
     */
    @Override
    public void delete(String id) {
        bannerDao.delete(id);
    }
    /**
     * 通过Id查找轮播图
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Banner selectByOne(String id) {
        Banner banner = bannerDao.selectByOne(id);
        return banner;
    }
    /**
     * 修改轮播图
     * @param banner
     */
    @Override
    public String update(Banner banner) {
        bannerDao.update(banner);
        return banner.getId();
    }

    /**
     * 获取总条数
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer selectPage() {
        Integer page = bannerDao.selectPage();
        return page;
    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public Map<String, Object> updateStatus(String id, String status) {
        Banner banner = new Banner();
        banner.setId(id);
        banner.setStatus(status);
        //bannerDao.update(banner);
        HashMap<String, Object> map = new HashMap<>();
        try {
            bannerDao.update(banner);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;
    }
}
