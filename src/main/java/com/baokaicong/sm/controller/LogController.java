package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Log;
import com.baokaicong.sm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 22:11:13
 */
@RestController
@RequestMapping("logs")
public class LogController {
    /**
     * 服务对象
     */
    @Autowired
    private LogService logService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @Authority("AU24521")
    @GetMapping("/{id}")
    public Log getOne(@PathVariable Long id) {
        return this.logService.queryById(id);
    }

    @Authority("AU24521")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Log log,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Log> list=logService.filter(log,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("logs",list);
        map.put("page",p);
        return map;
    }



    @Authority("AU24521")
    @PostMapping
    public boolean addLog(@NotNull Log log){
        return logService.insert(log)!=null;
    }


    @Authority("AU24521")
    @DeleteMapping
    public boolean drop(@NotNull Log log){
        return logService.deleteById(log.getId());
    }
}