package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (TAuth)表控制层
 *
 * @author makejava
 * @since 2020-05-11 18:26:21
 */
@RestController
@RequestMapping("/auths")
public class AuthController {
    /**
     * 服务对象
     */
    @Autowired
    private AuthService authService;

    /**
     * 通过主键查询单条数据
     *
     * @param auid 主键
     * @return 单条数据
     */
    @Authority("AU00006")
    @GetMapping("{auid}")
    public Auth getOne(@PathVariable String auid) {
        return this.authService.queryByAuid(auid);
    }

    /**
     * 查询所有权限
     * @return
     */
    @Authority("AU00006")
    @GetMapping
    public List<Auth> filter(@NotNull Auth auth,
                             @NotNull Order order){

        return authService.filter(auth);
    }

    /**
     * 根据条件筛选
     * @param page
     * @param per
     * @return
     */
    @Authority("AU00006")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull Auth auth,
                                      @PathVariable int page,
                                      @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<Auth> list=authService.filter(auth,p,order);
        Map<String,Object> map=new HashMap<String, Object>(){
            {
                put("auths",list);
                put("page",p);
            }
        };
        return map;
    }

    /**
     * 新增权限
     * @param auth
     * @return
     */
    @Authority
    @PostMapping
    public boolean insert(@NotNull Auth auth){
        authService.insert(auth);
        return true;
    }

    @Authority("AU00006")
    @PutMapping
    public boolean update(@NotNull Auth auth){
        return authService.update(auth);
    }

    @Authority
    @DeleteMapping
    public boolean delete(@NotNull Auth auth){
        return authService.deleteById(auth.getId());
    }
}