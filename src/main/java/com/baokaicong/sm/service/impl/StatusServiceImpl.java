package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.entity.Status;
import com.baokaicong.sm.dao.StatusDao;
import com.baokaicong.sm.service.StatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class StatusServiceImpl implements StatusService {
    @Resource
    private StatusDao statusDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Status queryById(Integer id) {
        return this.statusDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Status> queryByLimit(int offset, int limit) {
        return this.statusDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param status 实例对象
     * @return 实例对象
     */
    @Override
    public Status insert(Status status) {
        this.statusDao.insert(status);
        return status;
    }

    /**
     * 修改数据
     *
     * @param status 实例对象
     * @return 实例对象
     */
    @Override
    public Status update(Status status) {
        this.statusDao.update(status);
        return this.queryById(status.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.statusDao.deleteById(id) > 0;
    }
}