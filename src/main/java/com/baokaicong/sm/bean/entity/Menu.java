package com.baokaicong.sm.bean.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Setter
@Getter
@Accessors(chain = true)
public class Menu {
    
    private Integer id;
    
    private String mid;
    
    private String name;
    
    private String target;
    
    private String icon;
    
    private String parentMid;
    
    private String type;
    
    private String auid;

    private String auth;

    private Integer priority;

    @Override
    public boolean equals(Object obj){
        if(obj==null || !(obj instanceof Menu)){
            return false;
        }
        Menu menu=(Menu)obj;
        return this.mid.equals(menu.mid);
    }

}