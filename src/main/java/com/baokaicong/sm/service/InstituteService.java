package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Institute;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface InstituteService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Institute queryById(Integer id);

    /**
     * 通过iid查询单条数据
     * @param iid
     * @return
     */
    Institute queryByIid(String iid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Institute> queryByLimit(int offset, int limit);

    /**
     * 通过条件筛选数据，并且分页
     * @param institute
     * @param page
     * @return
     */
    List<Institute> filter(Institute institute,Page page, Order order);

    /**
     * 通过条件筛选数据
     * @param institute
     * @return
     */
    List<Institute> filter(Institute institute);

    /**
     * 新增数据
     *
     * @param institute 实例对象
     * @return 实例对象
     */
    Institute insert(Institute institute);

    /**
     * 修改数据
     *
     * @param institute 实例对象
     * @return 实例对象
     */
    boolean update(Institute institute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}