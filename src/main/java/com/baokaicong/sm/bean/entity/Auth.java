package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:11
 */
@Data
@Accessors(chain = true)
public class Auth {
    
    private Integer id;
    
    private String auid;
    
    private String name;
    
    private String token;


}