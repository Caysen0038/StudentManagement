package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.entity.Property;
import com.baokaicong.sm.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 18:26:28
 */
@RestController
@RequestMapping("properties")
public class PropertyController {
    /**
     * 服务对象
     */
    @Resource
    private PropertyService propertyService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Property getOne(@PathVariable Integer id) {
        return this.propertyService.queryById(id);
    }

    @Authority(open=true)
    @GetMapping("/filter")
    public Property getOne(@RequestParam @NotNull String name){
        return this.propertyService.queryByName(name);
    }

    @Authority("AU35723")
    @PostMapping
    public boolean insert(@NotNull Property property){
        return this.propertyService.insert(property)!=null;
    }

    @Authority("AU35723")
    @PutMapping
    public boolean update(@NotNull Property property){
        return this.propertyService.update(property);
    }

    @Authority("AU35723")
    @DeleteMapping
    public boolean delete(@NotNull Property property){
        return this.propertyService.deleteById(property.getId());
    }
}