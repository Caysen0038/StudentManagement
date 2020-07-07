package com.baokaicong.sm.bean.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author 包凯聪
 * @date 2020年6月2日
 */
@Data
@Accessors(chain = true)
public class TeacherCourse {
    private Long Id;
    private String tid;
    private String coid;
    private String cid;
    private String term;
    private String week;
    private String section;
    private Integer status;
    private Integer type;
    private String course;
    private String teacher;
    private String clazz;
}
