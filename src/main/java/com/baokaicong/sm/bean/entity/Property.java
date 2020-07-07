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
public class Property {
    
    private Integer id;
    
    private String name;
    
    private String value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}