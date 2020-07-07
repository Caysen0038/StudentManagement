package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.dao.AuthDao;
import com.baokaicong.sm.service.AuthService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:13
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthDao authDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Auth queryById(Integer id) {
        return this.authDao.queryById(id);
    }

    @Override
    public Auth queryByAuid(String auid) {
        return this.authDao.queryByAuid(auid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Auth> queryByLimit(int offset, int limit) {
        return this.authDao.queryAllByLimit(offset, limit);
    }

    /**
     * 根据条件筛选数据
     * @param auth
     * @param page
     * @return
     */
    @Override
    public List<Auth> filter(Auth auth, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Auth> list=authDao.queryAll(auth);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        
        return list;
    }
    /**
     * 根据条件筛选数据
     * @param auth
     * @return
     */
    @Override
    public List<Auth> filter(Auth auth) {
        List<Auth> list=authDao.queryAll(auth);
        return list;
    }

    /**
     * 新增数据
     *
     * @param auth 实例对象
     * @return 实例对象
     */
    @Override
    public Auth insert(Auth auth) {
        auth.setAuid(buildAuid());
        this.authDao.insert(auth);
        return auth;
    }

    /**
     * 修改数据
     *
     * @param auth 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Auth auth) {
        return this.authDao.update(auth)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.authDao.deleteById(id) > 0;
    }

    private String buildAuid(){
        Auth auth;
        String auid;
        while(true){
            auid="AU"+StringUtil.getRandomNumber(5);
            auth=authDao.queryByAuid(auid);
            if(auth==null){
                return auid;
            }
        }
    }
}