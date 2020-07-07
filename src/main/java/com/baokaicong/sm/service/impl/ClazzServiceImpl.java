package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Clazz;
import com.baokaicong.sm.bean.entity.Institute;
import com.baokaicong.sm.dao.ClazzDao;
import com.baokaicong.sm.service.ClazzService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Resource
    private ClazzDao clazzDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Clazz queryById(Integer id) {
        return this.clazzDao.queryById(id);
    }

    @Override
    public Clazz queryByCid(String cid) {
        return clazzDao.queryByCid(cid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Clazz> queryByLimit(int offset, int limit) {
        return this.clazzDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Clazz> filter(Clazz clazz, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Clazz> list=clazzDao.queryAll(clazz);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Clazz> filter(Clazz clazz) {
        List<Clazz> list=clazzDao.queryAll(clazz);
        return list;
    }

    /**
     * 新增数据
     *
     * @param clazz 实例对象
     * @return 实例对象
     */
    @Override
    public Clazz insert(Clazz clazz) {
        clazz.setCid(buildCid());
        this.clazzDao.insert(clazz);
        return clazz;
    }

    /**
     * 修改数据
     *
     * @param clazz 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Clazz clazz) {
        return this.clazzDao.update(clazz)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.clazzDao.deleteById(id) > 0;
    }

    private String buildCid(){
        Clazz clazz;
        String cid;
        while(true){
            cid="C"+ StringUtil.getRandomNumber(5);
            clazz=clazzDao.queryByCid(cid);
            if(clazz==null){
                return cid;
            }
        }
    }
}