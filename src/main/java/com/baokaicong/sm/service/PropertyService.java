package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.entity.Property;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface PropertyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Property queryById(Integer id);

    Property queryByName(String name);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Property> queryByLimit(int offset, int limit);



    /**
     * 新增数据
     *
     * @param property 实例对象
     * @return 实例对象
     */
    Property insert(Property property);

    /**
     * 修改数据
     *
     * @param property 实例对象
     * @return 实例对象
     */
    boolean update(Property property);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}