package com.baokaicong.sm.controller;

import com.baokaicong.sm.bean.entity.Status;
import com.baokaicong.sm.service.StatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 18:26:30
 */
@RestController
@RequestMapping("statuses")
public class StatusController {
    /**
     * 服务对象
     */
    @Resource
    private StatusService statusService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Status getOne(@PathVariable Integer id) {
        return this.statusService.queryById(id);
    }

}