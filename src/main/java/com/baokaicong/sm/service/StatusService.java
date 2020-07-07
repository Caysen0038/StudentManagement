package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.entity.Status;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface StatusService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Status queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Status> queryByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param status 实例对象
     * @return 实例对象
     */
    Status insert(Status status);

    /**
     * 修改数据
     *
     * @param status 实例对象
     * @return 实例对象
     */
    Status update(Status status);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}