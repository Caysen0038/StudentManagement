package com.baokaicong.sm.service;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.bean.entity.Student;
import com.baokaicong.sm.bean.entity.StudentCourse;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
public interface StudentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Student queryById(Integer id);

    /**
     * 通过sid查询单条数据
     * @param sid
     * @return
     */
    Student queryBySid(String sid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Student> queryByLimit(int offset, int limit);

    /**
     * 根据条件筛选数据，并且分页
     * @param student
     * @param page
     * @return
     */
    List<Student> filter(Student student, Page page, Order order);

    /**
     * 根据条件筛选数据
     * @param student
     * @return
     */
    List<Student> filter(Student student);

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    Student insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    boolean update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    boolean addCourse(Score score);

    boolean dropCourse(Score score);

    List<Score> filterCourse(Score score);

}