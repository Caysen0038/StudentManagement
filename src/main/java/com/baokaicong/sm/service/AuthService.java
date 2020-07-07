package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:12
 */
public interface AuthService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Auth queryById(Integer id);

    /**
     * 通过auid查询单条记录
     * @param auid
     * @return
     */
    Auth queryByAuid(String auid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Auth> queryByLimit(int offset, int limit);

    /**
     * 根据条件筛选，并且分页
     * @param auth
     * @return
     */
    List<Auth> filter(Auth auth,Page page, Order order);

    /**
     * 根据条件筛选
     * @param auth
     * @return
     */
    List<Auth> filter(Auth auth);

    /**
     * 新增数据
     *
     * @param auth 实例对象
     * @return 实例对象
     */
    Auth insert(Auth auth);

    /**
     * 修改数据
     *
     * @param auth 实例对象
     * @return 操作结果
     */
    boolean update(Auth auth);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}