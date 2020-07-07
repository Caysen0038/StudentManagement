package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.Score;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-28 21:40:12
 */
public interface ScoreService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Score queryById(Long id);

    /**
     * 通过auid查询单条记录
     * @param sid
     * @return
     */
    List<Score> queryBySid(String sid);

    /**
     * 通过
     * @param tid
     * @return
     */
    List<Score> queryByTid(String tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Score> queryByLimit(int offset, int limit);

    /**
     * 根据条件筛选，并且分页
     * @param score
     * @return
     */
    List<Score> filter(Score score,Page page, Order order);

    /**
     * 根据条件筛选
     * @param score
     * @return
     */
    List<Score> filter(Score score);

    /**
     * 新增数据
     *
     * @param score 实例对象
     * @return 实例对象
     */
    Score insert(Score score);

    /**
     * 修改数据
     *
     * @param score 实例对象
     * @return 操作结果
     */
    boolean update(Score score);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}