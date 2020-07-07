package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.*;
import com.baokaicong.sm.service.ScoreService;
import com.baokaicong.sm.service.TeacherService;
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
@RequestMapping("teachers")
public class TeacherController {
    /**
     * 服务对象
     */
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 通过主键查询单条数据
     *
     * @param tid 主键
     * @return 单条数据
     */
    @Authority("AU23467")
    @GetMapping("/{tid}")
    public Teacher getOne(@PathVariable String tid) {
        return this.teacherService.queryByTid(tid);
    }

    @Authority(open = true)
    @GetMapping
    public Teacher getSelf(@RequestAttribute("uid")String uid) {
        User user=userService.queryByUid(uid);
        if(user!=null){
            return this.teacherService.queryByTid(user.getAid());
        }
        return null;
    }

    @Authority("AU23467")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Teacher teacher,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Teacher> list=teacherService.filter(teacher,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("teachers",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU23467")
    @GetMapping("/filter")
    public List<Teacher> filter(@NotNull Teacher teacher){
        List<Teacher> list=teacherService.filter(teacher);
        return list;
    }

    @Authority("AU23467")
    @PostMapping
    public String insert(@NotNull Teacher teacher){
        return teacherService.insert(teacher).getTid();
    }

    @Authority("AU65324")
    @PutMapping
    public boolean updateSelf(@NotNull Teacher teacher,
                          @RequestAttribute("uid")String uid){
        User user=userService.queryByUid(uid);
        Teacher temp=teacherService.queryByTid(user.getAid());
        if(temp!=null){
            teacher.setId(temp.getId());
            return teacherService.update(teacher);
        }
        return false;
    }

    @Authority("AU23467")
    @PutMapping("/{tid}")
    public boolean update(@NotNull Teacher teacher,
                          @PathVariable("tid")String tid){
        Teacher temp=teacherService.queryByTid(tid);
        if(temp!=null){
            teacher.setId(temp.getId());
            return teacherService.update(teacher);
        }
        return false;
    }

    @Authority("AU23467")
    @DeleteMapping
    public boolean delete(@NotNull Teacher teacher){
        return teacherService.deleteById(teacher.getId());
}

    @Authority(open = true)
    @GetMapping("/courses")
    public List<TeacherCourse> getCourses(TeacherCourse teacherCourse,
                                          @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return new ArrayList<>();
        }
        teacherCourse.setTid(user.getAid());
        return teacherService.filterCourse(teacherCourse);
    }

    @Authority(open = true)
    @GetMapping("/scores")
    public List<Score> getCourseScore(Score score,
                                      @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return new ArrayList<>();
        }
        score.setTid(user.getAid());
        List<Score> list=scoreService.filter(score);
        return list;
    }

    @Authority("AU96420")
    @PatchMapping("/scores")
    public boolean saveScore(Score score,
                             @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return false;
        }
        if(!score.getTid().equals(user.getAid())){
            return false;
        }
        Score temp=scoreService.queryById(score.getId());
        if(score.getStatus()<2){
            temp.setScore(score.getScore())
                    .setStatus(1);
            return scoreService.update(temp);
        }
        return false;
    }

    @Authority("AU96420")
    @PutMapping("/scores")
    public boolean submitScore(Score score,
                               @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return false;
        }
        if(!score.getTid().equals(user.getAid())){
            return false;
        }
        Score temp=scoreService.queryById(score.getId());
        if(score.getStatus()<2){
            temp.setStatus(2);
            return scoreService.update(temp);
        }
        return false;
    }

    @Authority("AU96420")
    @PutMapping("/scores/rollback")
    public boolean rollbackScore(Score score,
                               @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return false;
        }
        if(!score.getTid().equals(user.getAid())){
            return false;
        }
        Score temp=scoreService.queryById(score.getId());
        if(score.getStatus()==2){
            temp.setStatus(3);
            return scoreService.update(temp);
        }
        return false;
    }

    @Authority("AU96420")
    @DeleteMapping("/scores/rollback")
    public boolean rollbackScoreCanceled(Score score,
                                 @RequestAttribute("uid") String uid){
        User user=userService.queryByUid(uid);
        if(user==null || !user.getAid().startsWith("T")){
            return false;
        }
        if(!score.getTid().equals(user.getAid())){
            return false;
        }
        Score temp=scoreService.queryById(score.getId());
        if(score.getStatus()==3){
            temp.setStatus(2);
            return scoreService.update(temp);
        }
        return false;
    }
}