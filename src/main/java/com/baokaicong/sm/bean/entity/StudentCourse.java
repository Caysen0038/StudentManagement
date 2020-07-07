package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentCourse {
    private Long id;
    private String sid;
    private String coid;
    private String time;
    private Integer status;
}
