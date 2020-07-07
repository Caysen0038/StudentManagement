package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Institute;
import com.baokaicong.sm.dao.InstituteDao;
import com.baokaicong.sm.service.InstituteService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class InstituteServiceImpl implements InstituteService {
    @Autowired
    private InstituteDao instituteDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Institute queryById(Integer id) {
        return this.instituteDao.queryById(id);
    }

    @Override
    public Institute queryByIid(String iid) {
        return instituteDao.queryByIid(iid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Institute> queryByLimit(int offset, int limit) {
        return this.instituteDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Institute> filter(Institute institute, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Institute> list=instituteDao.queryAll(institute);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Institute> filter(Institute institute) {
        return instituteDao.queryAll(institute);
    }

    /**
     * 新增数据
     *
     * @param institute 实例对象
     * @return 实例对象
     */
    @Override
    public Institute insert(Institute institute) {
        institute.setIid(buildIid());
        this.instituteDao.insert(institute);
        return institute;
    }

    /**
     * 修改数据
     *
     * @param institute 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Institute institute) {
        return this.instituteDao.update(institute)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.instituteDao.deleteById(id) > 0;
    }

    private String buildIid(){
        Institute institute;
        String iid;
        while(true){
            iid="I"+ StringUtil.getRandomNumber(5);
            institute=instituteDao.queryByIid(iid);
            if(institute==null){
                return iid;
            }
        }
    }
}