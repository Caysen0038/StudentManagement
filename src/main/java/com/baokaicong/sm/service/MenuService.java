package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Menu;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface MenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Menu queryById(Integer id);

    /**
     * 通过mid查询单条数据
     * @param mid
     * @return
     */
    Menu queryByMid(String mid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Menu> queryByLimit(int offset, int limit);

    /**
     * 根据条件筛选数据，并且分页
     * @param menu
     * @param page
     * @return
     */
    List<Menu> filter(Menu menu, Page page, Order order);

    /**
     * 根据条件筛选数据
     * @param menu
     * @return
     */
    List<Menu> filter(Menu menu);

    /**
     * 根据用户展示菜单
     * @param uid
     * @return
     */
    List<Menu> listUserMenus(String uid);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    Menu insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    boolean update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}