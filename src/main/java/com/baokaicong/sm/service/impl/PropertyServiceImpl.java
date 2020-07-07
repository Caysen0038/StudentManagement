package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.entity.Property;
import com.baokaicong.sm.dao.PropertyDao;
import com.baokaicong.sm.service.PropertyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    @Resource
    private PropertyDao propertyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Property queryById(Integer id) {
        return this.propertyDao.queryById(id);
    }

    @Override
    public Property queryByName(String name) {
        return propertyDao.queryByName(name);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Property> queryByLimit(int offset, int limit) {
        return this.propertyDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param property 实例对象
     * @return 实例对象
     */
    @Override
    public Property insert(Property property) {
        this.propertyDao.insert(property);
        return property;
    }

    /**
     * 修改数据
     *
     * @param property 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Property property) {
        return this.propertyDao.update(property)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.propertyDao.deleteById(id) > 0;
    }
}