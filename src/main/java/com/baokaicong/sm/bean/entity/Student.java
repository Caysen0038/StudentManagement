package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Data
@Accessors(chain = true)
public class Student {
    
    private Integer id;
    
    private String sid;
    
    private String name;
    
    private String sex;
    
    private String cid;
    
    private String iid;

    private String grade;

    private String clazz;

    private String institute;

    private String img;

    private String mobile;
}