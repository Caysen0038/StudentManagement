package com.baokaicong.sm.bean.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author 包凯聪
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Score {
    private Long id;
    private String sid;
    private String coid;
    private Float score;
    private String tid;
    private String term;
    private String week;
    private String section;
    private Integer credit;
    private Integer status;
    private Integer type;
    private String clazz;
    private String student;
    private String teacher;
    private String cid;
    private String course;

    @Override
    public boolean equals(Object src){
        if(src instanceof Score){
            Score score=(Score)src;
            return score.getSid().equals(this.sid)
                    && score.getCoid().equals(this.coid)
                    && score.getTerm().equals(this.term)
                    && score.getTid().equals(this.tid);

        }
        return false;
    }
}
