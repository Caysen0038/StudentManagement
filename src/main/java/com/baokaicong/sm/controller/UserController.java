package com.baokaicong.sm.controller;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.Result;
import com.baokaicong.sm.bean.UserToken;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.consts.PropertyName;
import com.baokaicong.sm.global.GlobalContext;
import com.baokaicong.sm.service.UserService;
import com.baokaicong.sm.util.StringUtil;
import com.baokaicong.sm.util.TokenUtil;
import org.aidework.core.data.MessageHelper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (TUser)表控制层
 *
 * @author 包凯聪
 * @since 2020-05-11 18:26:32
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private GlobalContext globalContext;

    @Autowired
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param uid uid
     * @return 单条数据
     */
    @Authority("AU00004")
    @GetMapping("/{uid}")
    public User getOne(@PathVariable String uid) {

        return this.userService.queryByUid(uid)
                .setPassword("");
    }

    @Authority(open = true)
    @GetMapping
    public User getSelf(@RequestAttribute("uid")String uid){
        return this.userService.queryByUid(uid)
                .setPassword("");
    }

    @Authority("AU00003")
    @PostMapping
    public String insert(@NotNull User user){
        user=userService.insert(user);
        return user.getUid();
    }

    @Authority(open = true)
    @PostMapping("/{uid}")
    public Result login(@PathVariable String uid,
                        @NotNull String password,
                        HttpServletRequest request){
        boolean isUid=isUid(uid);
        User user=isUid?userService.queryByUid(uid):userService.queryByUsername(uid);
        if(user!=null){
            boolean flag;
            if(isUid){
                flag=user.getUid().equals(uid);
            }else{
                flag=user.getUsername().equals(uid);
            }
            if(flag && user.getPassword().equals(password)){
                UserToken userToken=UserToken.builder()
                        .info(request.getRemoteAddr())
                        .uid(user.getUid())
                        .key(globalContext.getParam(PropertyName.TOKEN_KEY).toString())
                        .build();
                String token= TokenUtil.buildUserToken(userToken)?userToken.getToken():"";
                return Result.builder()
                        .code("200")
                        .data(token)
                        .build()
                        .generateTime();
            }
        }
        return Result.builder()
                .code("401")
                .build()
                .generateTime();
    }

    @Authority("AU00004")
    @PutMapping
    public boolean update(@NotNull User user){
        user.setPassword("")
                .setTime("");
        return userService.update(user);
    }

    @Authority("AU00004")
    @PatchMapping("/{uid}")
    public boolean resetPassword(@PathVariable("uid") String uid){
        if(StringUtil.isEmpty(uid)){
            return false;
        }
        User user=userService.queryByUid(uid);
        return userService.update(new User().setPassword(MessageHelper.MD5("123456")).setId(user.getId()));
    }

    @Authority("AU00004")
    @PatchMapping
    public boolean modifyPassword(@RequestAttribute("uid")String uid,
                                  String password){
        if(StringUtil.isEmpty(password)){
            return false;
        }
        User user=userService.queryByUid(uid);
        return userService.update(new User().setPassword(password).setId(user.getId()));
    }

    @Authority("AU00004")
    @DeleteMapping
    public boolean delete(User user){
        return userService.deleteById(user.getId());
    }


    @Authority("AU00004")
    @GetMapping("/{page}/{per}")
    public Map<String,Object> filter(@NotNull User user,
                                     @PathVariable int page,
                                     @PathVariable int per,
                                     @NotNull Order order){
        Page p=new Page()
                .setCurrent(page)
                .setPer(per);
        List<User> list=userService.filter(user,p,order);
        for(User u:list){
            u.setPassword("");
        }
        Map<String,Object> map=new HashMap<String, Object>(){
            {
                put("users",list);
                put("page",p);
            }
        };

        return map;
    }

    /**
     * 判断字符串是否为UID
     * UID规则： U开头，length=6,并且后5位为数字
     * @param str
     * @return
     */
    private boolean isUid(String str){
        try{
            if(str.length()==6
                    && str.startsWith("U")
                    && Integer.parseInt(str.substring(1,str.length()))>0){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }

    }
}