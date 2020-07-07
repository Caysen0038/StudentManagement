package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.StudentCourse;
import com.baokaicong.sm.dao.CourseDao;
import com.baokaicong.sm.dao.StudentCourseDao;
import com.baokaicong.sm.service.CourseService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Override
    public Course queryById(Long id) {
        return courseDao.queryById(id);
    }

    @Override
    public Course queryByCoid(String coid) {
        return courseDao.queryByCoid(coid);
    }

//    @Override
//    public List<Course> queryByTid(String tid, Page page) {
//        PageHelper.startPage(page.getCurrent(), page.getPer());
//        List<Course> list=courseDao.queryAll(new Course().setTid(tid));
//        PageInfo pageInfo = new PageInfo(list);
//        page.setTotal(pageInfo.getPages());
//        page.setCurrent(pageInfo.getPageNum());
//        return list;
//    }
//
//    @Override
//    public List<Course> queryBySid(String sid, Page page) {
//        PageHelper.startPage(page.getCurrent(), page.getPer());
//        List<Course> list=courseDao.queryBySid(sid);
//        PageInfo pageInfo = new PageInfo(list);
//        page.setTotal(pageInfo.getPages());
//        page.setCurrent(pageInfo.getPageNum());
//        return list;
//    }

    @Override
    public List<Course> queryByLimit(int offset, int limit) {
        return courseDao.queryAllByLimit(offset,limit);
    }

    @Override
    public List<Course> filter(Course course, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Course> list=courseDao.queryAll(course);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Course> filter(Course course) {
        List<Course> list=courseDao.queryAll(course);
        return list;
    }

    @Override
    public Course insert(Course course) {
        course.setCoid(buildCoid());
        courseDao.insert(course);
        return course;
    }

    @Override
    public boolean update(Course course) {
        return courseDao.update(course)==1;
    }

    @Override
    public boolean deleteById(Long id) {
        return courseDao.deleteById(id)==1;
    }

    private String buildCoid(){
        String coid;
        while(true){
            coid="CO"+ StringUtil.getRandomNumber(5);
            if(courseDao.queryByCoid(coid)==null){
                return coid;
            }
        }
    }
}
