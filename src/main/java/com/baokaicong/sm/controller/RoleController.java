package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Role;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.util.StringUtil;
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
 * @since 2020-05-11 18:26:29
 */
@RestController
@RequestMapping("roles")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 通过主键查询单条数据
     *
     * @param rid 主键
     * @return 单条数据
     */
    @Authority("AU38263")
    @GetMapping("/{rid}")
    public Role getOne(@PathVariable String rid) {
        return this.roleService.queryByRid(rid);
    }

    @Authority("AU38263")
    @GetMapping
    public List<Role> filter(@NotNull Role role){
        return roleService.filter(role);
    }

    @Authority("AU38263")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Role role,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Role> list=roleService.filter(role,p,order);
        Map<String,Object> map=new HashMap<>();
        map.put("roles",list);
        map.put("page",p);
        return map;
    }


    @Authority("AU38263")
    @PostMapping
    public String insert(@NotNull Role role){
        return roleService.insert(role).getRid();
    }

    @Authority("AU38263")
    @PutMapping
    public boolean update(@NotNull Role role){
        return roleService.update(role);
    }

    @Authority("AU38263")
    @DeleteMapping
    public boolean delete(@NotNull Role role){
        return roleService.deleteById(role.getId());
    }

    @Authority("AU38263")
    @GetMapping("/{rid}/auths")
    public List<Auth> listAuths(@PathVariable String rid){
        if(StringUtil.isEmpty(rid)){
            return new ArrayList<>();
        }
        return roleService.listAuthByRid(rid);
    }

    @Authority("AU38263")
    @PostMapping("/{rid}/auths")
    public boolean addAuth(@PathVariable String rid,
                           @NotNull String auid){
        return roleService.addAuth(rid,auid);
    }

    @Authority("AU38263")
    @DeleteMapping("/{rid}/auths")
    public boolean dropAuth(@PathVariable String rid,
                            @NotNull String auid){
        return roleService.dropAuth(rid,auid);
    }

}