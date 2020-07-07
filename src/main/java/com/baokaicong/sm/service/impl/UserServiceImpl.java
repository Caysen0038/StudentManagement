package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.dao.UserDao;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.service.UserService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aidework.core.data.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    @Override
    public User queryByUsername(String username) {
        return userDao.queryByUsername(username);
    }

    @Override
    public User queryByUid(String uid) {
        return userDao.queryByUid(uid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 根据条件查询多条数据
     * @param user 条件存储对象
     * @param page
     * @return
     */
    @Override
    public List<User> filter(User user,Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<User> list=userDao.queryAll(user);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    /**
     * 根据条件查询多条数据
     * @param user 条件存储对象
     * @return
     */
    @Override
    public List<User> filter(User user) {
        List<User> list=userDao.queryAll(user);
        return list;
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        user.setUid(buildUID())
                .setTime(System.currentTimeMillis()+"")
                .setPassword(MessageHelper.MD5("123456"))
                .setToken(System.currentTimeMillis()+"");
        this.userDao.insert(user);

        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(User user) {

        return this.userDao.update(user)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    private String buildUID(){
        User user;
        String uid;
        int year=new Date().getYear();
        while(true){
            uid="U"+year+ StringUtil.getRandomNumber(4);
            user=userDao.queryByUid(uid);
            if(user==null){
                return uid;
            }
        }
    }
}