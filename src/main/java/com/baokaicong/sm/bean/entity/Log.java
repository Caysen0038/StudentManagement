package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 22:11:12
 */
@Data
@Accessors(chain = true)
public class Log {
    
    private Long id;
    
    private Integer level;
    
    private String info;
    
    private String uid;
    
    private String time;

}