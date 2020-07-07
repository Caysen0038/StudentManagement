package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Role;
import java.util.List;

/**
 * (TRole)表服务接口
 *
 * @author makejava
 * @since 2020-05-11 21:40:14
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role queryById(Integer id);

    /**
     * 通过rid查询单条数据
     * @param rid
     * @return
     */
    Role queryByRid(String rid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Role> queryByLimit(int offset, int limit);

    /**
     * 列出该角色的权限列表
     * @param rid
     * @return
     */
    List<Auth> listAuthByRid(String rid);

    /**
     * 通过条件筛选数据,并且分页
     * @param role
     * @param page
     * @return
     */
    List<Role> filter(Role role, Page page, Order order);

    /**
     * 通过条件筛选数据
     * @param role
     * @return
     */
    List<Role> filter(Role role);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    boolean update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 为角色添加权限
     * @param rid
     * @param auid
     * @return
     */
    boolean addAuth(String rid,String auid);

    /**
     * 为角色移除权限
     * @param rid
     * @param auid
     * @return
     */
    boolean dropAuth(String rid,String auid);
}