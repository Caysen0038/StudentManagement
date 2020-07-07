package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.*;
import com.baokaicong.sm.dao.ScoreDao;
import com.baokaicong.sm.dao.StudentCourseDao;
import com.baokaicong.sm.dao.StudentDao;
import com.baokaicong.sm.dao.UserDao;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.service.StudentService;
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
public class StudentServiceImpl implements StudentService {
    private static final String ROLE_STUDENT="R21154";
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ScoreDao scoreDao;

    @Autowired
    private CourseServiceImpl courseService;

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
    public Student queryById(Integer id) {
        return this.studentDao.queryById(id);
    }

    @Override
    public Student queryBySid(String sid) {
        return studentDao.queryBySid(sid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Student> queryByLimit(int offset, int limit) {
        return this.studentDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Student> filter(Student student, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Student> list=studentDao.queryAll(student);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Student> filter(Student student) {
        List<Student> list=studentDao.queryAll(student);
        return list;
    }

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @Override
    public Student insert(Student student) {
        student.setSid(buildSid());
        this.studentDao.insert(student);
        userService.insert(new User()
                .setUsername(student.getSid())
                .setAid(student.getSid())
                .setRid(ROLE_STUDENT)
        );


        return student;
    }

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Student student) {
        return this.studentDao.update(student)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.studentDao.deleteById(id) > 0;
    }

    @Override
    public boolean addCourse(Score score) {
        List<Score> list=scoreDao.queryAll(
                new Score()
                        .setCoid(score.getCoid())
                        .setSid(score.getSid())
                        .setTerm(score.getTerm()));
        if(list.size()==0){
            Course course=courseService.queryByCoid(score.getCoid());
            if(course!=null){
                score.setType(course.getType());
                return scoreDao.insert(score)==1;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean dropCourse(Score score) {
//        List<Score> list=scoreDao.queryAll(
//                new Score()
//                        .setCoid(score.getCoid())
//                        .setSid(score.getSid())
//                        .setTerm(score.getTerm()));
//        if(list.size()>0){
            return scoreDao.deleteById(score.getId())==1;
//        }
//        return false;
    }

    @Override
    public List<Score> filterCourse(Score score) {
        return scoreDao.queryAll(score);
    }

    private String buildSid(){
        Student student;
        String sid;
        String year=new Date().getYear()+"";
        while(true){
            sid="S"+year+ StringUtil.getRandomNumber(5);
            student=studentDao.queryBySid(sid);
            if(student==null){
                return sid;
            }
        }
    }
}