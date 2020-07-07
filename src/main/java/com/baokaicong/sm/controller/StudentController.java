package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.bean.entity.Student;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.exception.SMException;
import com.baokaicong.sm.service.ScoreService;
import com.baokaicong.sm.service.StudentService;
import com.baokaicong.sm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 18:26:31
 */
@RestController
@RequestMapping("students")
public class StudentController {
    /**
     * 服务对象
     */
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 通过主键查询单条数据
     *
     * @param sid 主键
     * @return 单条数据
     */
    @Authority("AU23421")
    @GetMapping("/{sid}")
    public Student getOne(@PathVariable String sid) {
        return this.studentService.queryBySid(sid);
    }

    @Authority(open = true)
    @GetMapping
    public Student getSelf(@RequestAttribute("uid") String uid) {
        User user=userService.queryByUid(uid);
        if(user!=null){
            return this.studentService.queryBySid(user.getAid());
        }
        return null;
    }

    @Authority("AU23421")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Student student,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setPer(per)
                .setCurrent(page);
        List<Student> list=studentService.filter(student,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("students",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU23421")
    @GetMapping("/filter")
    public List<Student> filter(@NotNull Student student){
        List<Student> list=studentService.filter(student);
        return list;
    }

    @Authority("AU23421")
    @PostMapping
    public String insert(@NotNull Student student){
        return studentService.insert(student).getSid();
    }

    @Authority("AU23146")
    @PutMapping
    public boolean updateSelf(@NotNull Student student,
                          @RequestAttribute("uid")String uid){
        User user=userService.queryByUid(uid);
        Student temp=studentService.queryBySid(user.getAid());
        if(temp!=null){
            student.setId(temp.getId());
            return studentService.update(student);
        }
        return false;
    }

    @Authority("AU23421")
    @PutMapping("/{sid}")
    public boolean update(@NotNull Student student,
                          @PathVariable("sid") String sid){
         Student temp=studentService.queryBySid(sid);
         if(temp!=null){
             student.setId(temp.getId());
             return studentService.update(student);
         }
        return false;
    }

    @Authority("AU23421")
    @DeleteMapping
    public boolean delete(@NotNull Student student){
        return studentService.deleteById(student.getId());
    }

    @Authority("AU19882")
    @GetMapping("/scores")
    public List<Score> getStudentScore(@NotNull String term,
                                       @RequestAttribute("uid")String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("S")){
            throw new SMException("403","该用户不具备查询个人成绩权限");
        }
        Score score=new Score()
                .setSid(user.getAid())
                .setTerm(term)
                .setStatus(2);
        List<Score> list=scoreService.filter(score);
        return list;
    }
}