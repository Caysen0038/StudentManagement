package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.entity.Menu;
import com.baokaicong.sm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private MenuService menuService;

    @Authority(open = true)
    @GetMapping("/menu")
    public List<Menu> list(@RequestAttribute("uid")String uid) {
        return menuService.listUserMenus(uid);
    }


}
