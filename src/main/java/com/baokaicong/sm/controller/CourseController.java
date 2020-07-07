package com.baokaicong.sm.controller;


import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.*;
import com.baokaicong.sm.service.*;
import org.aidework.core.object.BeanDuplicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private BeanDuplicator beanDuplicator;

    @Authority("AU09711")
    @GetMapping("/{coid}")
    public Course getOne(@PathVariable("coid") String coid){
        return courseService.queryByCoid(coid);
    }

    @Authority(open=true)
    @GetMapping
    public List getSelfCourses(@RequestAttribute("uid") String uid,
                                       @NotNull String term){
        User user=userService.queryByUid(uid);
        if(user==null || user.getAid()==null || user.getAid().length()==0){
            return new ArrayList();
        }
        String aid=user.getAid();
        List<Course> courseList=new ArrayList<>();
        if(aid.startsWith("S")){
            List<Score> list=studentService.filterCourse(
                    new Score()
                            .setSid(aid)
                            .setTerm(term));
            return list;
        }else if(aid.startsWith("T")){
            List<TeacherCourse> list=teacherService.filterCourse(
                    new TeacherCourse()
                            .setTid(aid)
                            .setTerm(term));
            return list;
        }
        return new ArrayList();
    }

    @Authority("AU09711")
    @PostMapping
    public boolean insert(@NotNull Course course){
        return courseService.insert(course)!=null;
    }

    @Authority("AU09711")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Course course,
                                     @PathVariable("page")int page,
                                     @PathVariable("per")int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setPer(per)
                .setCurrent(page);
        List<Course> list=courseService.filter(course,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("courses",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU09711")
    @GetMapping("/filter")
    public List<Course> filter(@NotNull Course course){
        List<Course> list=courseService.filter(course);
        return list;
    }

    @Authority("AU09711")
    @PutMapping
    public boolean update(@NotNull Course course){
        return courseService.update(course);
    }

    @Authority("AU09711")
    @DeleteMapping
    public boolean delete(@NotNull Course course){
        return courseService.deleteById(course.getId());
    }


    @Authority("AU76584")
    @PostMapping("/teachers")
    public boolean teachersCourse(@NotNull TeacherCourse teacherCourse){
        teacherCourse.setStatus(0);
        if(teacherService.addCourse(teacherCourse)){
            List<Student> studentList=studentService.filter(new Student().setCid(teacherCourse.getCid()));
            int n=0;
            Score score;
            for(Student stu:studentList){
                score=beanDuplicator.duplicate(teacherCourse,Score.class);
                        score.setSid(stu.getSid())
                        .setStatus(0)
                        .setScore(0f);
                n+=studentService.addCourse(score)?1:0;
            }
            return n==studentList.size();
        }
        return false;
    }

    @Authority("AU76584")
    @GetMapping("/teachers")
    public List<TeacherCourse> filterTeachersCourse(@NotNull TeacherCourse teacherCourse){
        List<TeacherCourse> list=teacherService.filterCourse(teacherCourse);
        return list;
    }

    @Authority("AU76584")
    @DeleteMapping("/teachers")
    public boolean deleteTeachersCourse(@NotNull TeacherCourse teacherCourse){
        teacherCourse=teacherService.queryCourseById(teacherCourse.getId());
        if(teacherCourse!=null && teacherService.dropCourse(teacherCourse)){
            Score score=beanDuplicator.duplicate(teacherCourse,Score.class);
            score.setStatus(null);
            List<Score> list=scoreService.filter(score);
            for(Score s:list){
                scoreService.deleteById(s.getId());
            }
            return true;
        }
        return false;
    }

    @Authority("AU76584")
    @PostMapping("/students")
    public boolean studentCourse(@NotNull Score score){
        return studentService.addCourse(score);
    }

    @Authority("AU76584")
    @GetMapping("/students")
    public List<Score> filterStudentCourse(@NotNull Score score){
        return scoreService.filter(new Score().setSid(score.getSid()));
    }

    @Authority("AU76584")
    @DeleteMapping("/students")
    public boolean deleteStudentCourse(@NotNull Score score){
        return scoreService.deleteById(score.getId());
    }

    @Authority("AU76584")
    @GetMapping("/clazzs")
    public List<TeacherCourse> filterClazzCourse(@NotNull TeacherCourse teacherCourse){
        List<TeacherCourse> list=teacherService.filterCourse(teacherCourse);
        return list;
    }

    @Authority("AU76584")
    @DeleteMapping("/clazzs")
    public boolean deleteClazzCourse(@NotNull TeacherCourse teacherCourse){
        teacherCourse=teacherService.queryCourseById(teacherCourse.getId());
        if(teacherCourse!=null && teacherCourse.getCid()!=null
                && teacherCourse.getCid().startsWith("C")
                && teacherService.dropCourse(teacherCourse)){
            List<Student> studentList=studentService.filter(new Student().setCid(teacherCourse.getCid()));
            Score score;
            for(Student stu:studentList){
                score=beanDuplicator.duplicate(teacherCourse,Score.class);
                score.setSid(stu.getSid())
                        .setStatus(null);
                List<Score> list=scoreService.filter(score);
                for(Score s:list){
                    scoreService.deleteById(s.getId());
                }
            }

            return true;
        }
        return false;
    }

    @Authority("AU87449")
    @GetMapping("/select")
    public List<TeacherCourse> getSelectCourse(){
        Property property=propertyService.queryByName("CURRENT_TERM");
        return teacherService.filterCourse(
                new TeacherCourse()
                        .setTerm(property.getValue())
                        .setType(1)
        );
    }

    @Authority("AU87449")
    @PostMapping("/select")
    public boolean selectCourse(Score score){
        if(score.getSid()==null || score.getSid().length()==0){
            return false;
        }
        score.setScore(0f);
        return studentService.addCourse(score);
    }
}
