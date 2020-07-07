package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Menu;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.dao.MenuDao;
import com.baokaicong.sm.dao.UserDao;
import com.baokaicong.sm.service.MenuService;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.service.UserService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Menu queryById(Integer id) {
        return this.menuDao.queryById(id);
    }

    @Override
    public Menu queryByMid(String mid) {
        return menuDao.queryByMid(mid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Menu> queryByLimit(int offset, int limit) {
        return this.menuDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Menu> filter(Menu menu, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Menu> list=menuDao.queryAll(menu);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Menu> filter(Menu menu) {
        List<Menu> list=menuDao.queryAll(menu);
        return list;
    }

    @Override
    public List<Menu> listUserMenus(String uid) {
        User user=userDao.queryByUid(uid);
        List<Menu> list=new ArrayList<>();
        if(user==null){
            return list;
        }
        List<Auth> auths=roleService.listAuthByRid(user.getRid());
        List<Menu> menus;
        Menu condition=new Menu();
        for(Auth auth:auths){
            condition.setAuid(auth.getAuid());
            menus=menuDao.queryAll(condition);
            for(Menu m:menus){
                if(!list.contains(m)){
                    list.add(m);
                }
            }
        }
        // 通过priority字段对menu进行排序前台显示顺序
        Collections.sort(list, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o2.getPriority()-o1.getPriority();
            }
        });
        return list;
    }

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu insert(Menu menu) {
        menu.setMid(buildMid());
        this.menuDao.insert(menu);
        return menu;
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Menu menu) {
        return this.menuDao.update(menu)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.menuDao.deleteById(id) > 0;
    }

    private String buildMid(){
        Menu menu;
        String mid;
        while(true){
            mid="M"+ StringUtil.getRandomNumber(5);
            menu=menuDao.queryByMid(mid);
            if(menu==null){
                return mid;
            }
        }
    }
}