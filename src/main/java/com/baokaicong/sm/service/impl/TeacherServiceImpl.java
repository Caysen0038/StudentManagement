package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.*;
import com.baokaicong.sm.dao.CourseDao;
import com.baokaicong.sm.dao.TeacherCourseDao;
import com.baokaicong.sm.dao.TeacherDao;
import com.baokaicong.sm.service.CourseService;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.service.TeacherService;
import com.baokaicong.sm.service.UserService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    private static final String ROLE_TEACHER="R58888";

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Teacher queryById(Long id) {
        return this.teacherDao.queryById(id);
    }

    @Override
    public Teacher queryByTid(String tid) {
        return teacherDao.queryByTid(tid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Teacher> queryByLimit(int offset, int limit) {
        return this.teacherDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Teacher> filter(Teacher teacher, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Teacher> list=teacherDao.queryAll(teacher);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Teacher> filter(Teacher teacher) {
        List<Teacher> list=teacherDao.queryAll(teacher);
        return list;
    }

    /**
     * 新增数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    @Override
    public Teacher insert(Teacher teacher) {
        teacher.setTid(buildTid());
        this.teacherDao.insert(teacher);
        userService.insert(new User()
                .setUsername(teacher.getTid())
                .setAid(teacher.getTid())
                .setRid(ROLE_TEACHER)
        );

        return teacher;
    }

    /**
     * 修改数据
     *
     * @param teacher 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Teacher teacher) {
        return this.teacherDao.update(teacher)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.teacherDao.deleteById(id) > 0;
    }

    @Override
    public boolean addCourse(TeacherCourse teacherCourse) {
        List<TeacherCourse> list=teacherCourseDao.queryAll(
                new TeacherCourse()
                        .setCoid(teacherCourse.getCoid())
                        .setTid(teacherCourse.getTid())
                        .setCid(teacherCourse.getCid()));
        if(list.size()==0){
            Course course=courseService.queryByCoid(teacherCourse.getCoid());
            if(course!=null){
                teacherCourse.setType(course.getType());
                return teacherCourseDao.insert(teacherCourse)==1;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<TeacherCourse> filterCourse(TeacherCourse teacherCourse) {
        return teacherCourseDao.queryAll(teacherCourse);
    }

    @Override
    public boolean dropCourse(TeacherCourse teacherCourse) {
//        List<TeacherCourse> list=teacherCourseDao.queryAll(
//                new TeacherCourse()
//                        .setCoid(teacherCourse.getCoid())
//                        .setTid(teacherCourse.getTid())
//                        .setCid(teacherCourse.getCid()));
//        if(list.size()>0){
        return teacherCourseDao.deleteById(teacherCourse.getId())==1;
//        }
//        return false;
    }

    @Override
    public TeacherCourse queryCourseById(Long id) {
        return teacherCourseDao.queryById(id);
    }

    private String buildTid(){
        Teacher teacher;
        String tid;
        String year=new Date().getYear()+"";
        while(true){
            tid="T"+year+ StringUtil.getRandomNumber(5);
            teacher=teacherDao.queryByTid(tid);
            if(teacher==null){
                return tid;
            }
        }
    }
}