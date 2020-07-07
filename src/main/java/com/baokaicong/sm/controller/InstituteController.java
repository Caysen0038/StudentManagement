package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Institute;
import com.baokaicong.sm.service.InstituteService;
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
 * @since 2020-05-11 18:26:27
 */
@RestController
@RequestMapping("insts")
public class InstituteController {
    /**
     * 服务对象
     */
    @Autowired
    private InstituteService instituteService;

    /**
     * 通过iid查询单条数据
     *
     * @param iid iid
     * @return 单条数据
     */
    @Authority("AU58723")
    @GetMapping("/{iid}")
    public Institute getOne(@PathVariable String iid) {
        return this.instituteService.queryByIid(iid);
    }

    @Authority("AU58723")
    @GetMapping
    public List<Institute> filter(@NotNull Institute institute,
                                  @NotNull Order order){
        return instituteService.filter(institute);
    }

    @Authority("AU58723")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Institute institute,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Institute> list=instituteService.filter(institute,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("insts",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU58723")
    @PostMapping
    public String insert(@NotNull Institute institute){
        return instituteService.insert(institute).getIid();
    }

    @Authority("AU58723")
    @PutMapping
    public boolean update(@NotNull Institute institute){
        return instituteService.update(institute);
    }

    @Authority("AU58723")
    @DeleteMapping
    public boolean delete(@NotNull Institute institute){
        return instituteService.deleteById(institute.getId());
    }

}