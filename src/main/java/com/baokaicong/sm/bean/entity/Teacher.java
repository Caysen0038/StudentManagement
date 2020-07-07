package com.baokaicong.sm.bean.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Data
@Accessors(chain = true)
public class Teacher{
    
    private Integer id;
    
    private String tid;
    
    private String name;
    
    private String sex;
    
    private String iid;

    private String institute;

    private String img;

    private String mobile;
}