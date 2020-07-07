package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Teacher;
import com.baokaicong.sm.bean.entity.TeacherCourse;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface TeacherService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Teacher queryById(Long id);

    /**
     * 通过tid查询单条数据
     * @param tid
     * @return
     */
    Teacher queryByTid(String tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Teacher> queryByLimit(int offset, int limit);

    /**
     * 通过条件筛选，并且分页
     * @param teacher
     * @param page
     * @return
     */
    List<Teacher> filter(Teacher teacher, Page page, Order order);

    /**
     * 通过条件筛选
     * @param teacher
     * @return
     */
    List<Teacher> filter(Teacher teacher);

    /**
     * 新增数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    Teacher insert(Teacher teacher);

    /**
     * 修改数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    boolean update(Teacher teacher);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    boolean addCourse(TeacherCourse teacherCourse);

    List<TeacherCourse> filterCourse(TeacherCourse teacherCourse);

    boolean dropCourse(TeacherCourse teacherCourse);

    TeacherCourse queryCourseById(Long id);
}