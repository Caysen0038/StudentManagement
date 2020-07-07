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
public class Clazz{
    
    private Integer id;
    
    private String cid;
    
    private String name;
    
    private String iid;

    private String grade;

    private String institute;

}