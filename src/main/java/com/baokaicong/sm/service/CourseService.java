package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.TeacherCourse;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-28 21:40:12
 */
public interface CourseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Course queryById(Long id);

    /**
     * 通过auid查询单条记录
     * @param coid
     * @return
     */
    Course queryByCoid(String coid);

//    /**
//     * 通过tid查询多条数据
//     * @param tid
//     * @return
//     */
//    List<Course> queryByTid(String tid,Page page);
//
//    /**
//     * 通过sid查询多条数据
//     * @param sid
//     * @return
//     */
//    List<Course> queryBySid(String sid,Page page);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Course> queryByLimit(int offset, int limit);

    /**
     * 根据条件筛选，并且分页
     * @param course
     * @return
     */
    List<Course> filter(Course course,Page page, Order order);

    /**
     * 根据条件筛选
     * @param course
     * @return
     */
    List<Course> filter(Course course);

    /**
     * 新增数据
     *
     * @param course 实例对象
     * @return 实例对象
     */
    Course insert(Course course);

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 操作结果
     */
    boolean update(Course course);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}