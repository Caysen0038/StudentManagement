package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.service.CourseService;
import com.baokaicong.sm.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Authority("AU54534")
    @GetMapping("/{id}")
    public Score getOne(@PathVariable("id") Long id){
        return scoreService.queryById(id);
    }

    @Authority("AU54534")
    @PostMapping
    public boolean insert(@NotNull Score score){
        return scoreService.insert(score)!=null;
    }

    @Authority("AU54534")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Score score,
                                     @PathVariable("page")int page,
                                     @PathVariable("per")int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setPer(per)
                .setCurrent(page);
        List<Score> list=scoreService.filter(score,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("scores",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU54534")
    @GetMapping("/filter")
    public List<Score> filter(@NotNull Score score){
        List<Score> list=scoreService.filter(score);
        return list;
    }

    @Authority("AU54534")
    @PutMapping
    public boolean update(@NotNull Score score){
        return scoreService.update(score);
    }

    @Authority("AU54534")
    @DeleteMapping
    public boolean delete(@NotNull Score score){
        return scoreService.deleteById(score.getId());
    }

    @Authority("AU54534")
    @PutMapping("/rollback")
    public boolean rollback(@NotNull Score score){
        return scoreService.update(new Score().setId(score.getId()).setStatus(1));
    }

    @Authority("AU54534")
    @DeleteMapping("/rollback")
    public boolean rollbackReject(@NotNull Score score){
        return scoreService.update(new Score().setId(score.getId()).setStatus(2));
    }
}
