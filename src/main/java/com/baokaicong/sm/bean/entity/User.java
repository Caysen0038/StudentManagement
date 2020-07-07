package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    
    private String uid;
    
    private String username;
    
    private String password;
    
    private String time;
    
    private String token;
    
    private String aid;

    private String rid;

    private String role;
}