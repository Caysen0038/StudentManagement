package com.baokaicong.sm.bean.entity;

import java.io.Serializable;

/**
 * (TStatus)实体类
 *
 * @author makejava
 * @since 2020-05-11 21:40:14
 */
public class Status implements Serializable {
    private static final long serialVersionUID = -77619852932883565L;
    
    private Integer id;
    
    private String code;
    
    private String value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}