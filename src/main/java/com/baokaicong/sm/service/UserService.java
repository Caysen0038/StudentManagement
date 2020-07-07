package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.User;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 通过用户名查询单条数据
     * @param username 用户名
     * @return 实例对象
     */
    User queryByUsername(String username);

    /**
     * 通过UID查询单条数据
     * @param uid uid
     * @return 实例对象
     */
    User queryByUid(String uid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryByLimit(int offset, int limit);

    /**
     * 通过指定条件筛选多条数据，并且分页
     * @param user 条件存储对象
     * @param page
     * @return 对象列表
     */
    List<User> filter(User user,Page page, Order order);

    /**
     * 通过指定条件筛选多条数据
     * @param user 条件存储对象
     * @return 对象列表
     */
    List<User> filter(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}