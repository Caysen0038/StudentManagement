package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Log;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 22:11:13
 */
public interface LogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Log queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Log> queryByLimit(int offset, int limit);

    /**
     * 通过条件筛选数据，并且分页
     * @param log
     * @param page
     * @return
     */
    List<Log> filter(Log log, Page page, Order order);

    /**
     * 通过条件筛选数据
     * @param log
     * @return
     */
    List<Log> filter(Log log);

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    Log insert(Log log);

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    boolean update(Log log);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}