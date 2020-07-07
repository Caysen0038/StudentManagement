package com.baokaicong.sm.bean.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author 包凯聪
 */
@Data
@Accessors(chain = true)
public class Course {
    private Long id;
    private String coid;
    private String name;
    private Integer credit;
    private Integer type;
}
