package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Clazz;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface ClazzService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Clazz queryById(Integer id);

    /**
     * 通过cid查询单条数据
     * @param cid
     * @return
     */
    Clazz queryByCid(String cid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Clazz> queryByLimit(int offset, int limit);

    /**
     * 通过条件筛选数据，并且分页
     * @param clazz
     * @return
     */
    List<Clazz> filter(Clazz clazz,Page page, Order order);

    /**
     * 通过条件筛选数据
     * @param clazz
     * @return
     */
    List<Clazz> filter(Clazz clazz);

    /**
     * 新增数据
     *
     * @param clazz 实例对象
     * @return 实例对象
     */
    Clazz insert(Clazz clazz);

    /**
     * 修改数据
     *
     * @param clazz 实例对象
     * @return 实例对象
     */
    boolean update(Clazz clazz);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}