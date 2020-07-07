package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Clazz;
import com.baokaicong.sm.service.ClazzService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 18:26:26
 */
@RestController
@RequestMapping("clazzs")
public class ClazzController {
    /**
     * 服务对象
     */
    @Resource
    private ClazzService clazzService;

    /**
     * 通过主键查询单条数据
     *
     * @param cid 主键
     * @return 单条数据
     */
    @Authority("AU02323")
    @GetMapping("/{cid}")
    public Clazz getOne(@PathVariable String cid) {
        return this.clazzService.queryByCid(cid);
    }

    @Authority("AU02323")
    @GetMapping
    public List<Clazz> filter(@NotNull Clazz clazz,
                              @NotNull Order order){
        return clazzService.filter(clazz);
    }


    @Authority("AU02323")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Clazz clazz,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Clazz> list=clazzService.filter(clazz,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("clazzs",list);
        map.put("page",p);
        return map;
    }


    @Authority("AU02323")
    @PostMapping
    public String insert(@NotNull Clazz clazz){
        return clazzService.insert(clazz).getCid();
    }

    @Authority("AU02323")
    @PutMapping
    public boolean update(@NotNull Clazz clazz){
        return clazzService.update(clazz);
    }

    @Authority("AU02323")
    @DeleteMapping
    public boolean delete(@NotNull Clazz clazz){
        return clazzService.deleteById(clazz.getId());
    }
}