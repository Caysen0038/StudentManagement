package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Menu;
import com.baokaicong.sm.service.MenuService;
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
 * @since 2020-05-11 18:26:28
 */
@RestController
@RequestMapping("menus")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    @Authority("AU23561")
    @GetMapping("/{mid}")
    public Menu getOne(@PathVariable("mid")String mid){
        return menuService.queryByMid(mid);
    }

    @Authority("AU23561")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Menu menu,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setPer(per)
                .setCurrent(page);
        List<Menu> list=menuService.filter(menu,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("menus",list);
        map.put("page",p);
        return map;
    }

    @Authority("AU23561")
    @PostMapping
    public String insert(@NotNull Menu menu){
        return menuService.insert(menu).getMid();
    }

    @Authority("AU23561")
    @PutMapping
    public boolean update(@NotNull Menu menu){
        return menuService.update(menu);
    }

    @Authority("AU23561")
    @DeleteMapping
    public boolean delete(@NotNull Menu menu){
        return menuService.deleteById(menu.getId());
    }

}