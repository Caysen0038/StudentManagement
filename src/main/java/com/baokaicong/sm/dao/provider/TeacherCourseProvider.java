package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.StudentCourse;
import com.baokaicong.sm.bean.entity.TeacherCourse;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class TeacherCourseProvider {
    private static final String TABLE_NAME="t_teacher_course";
    private static final String VIEW_NAME="v_teacher_course";
    public String queryAll(TeacherCourse teacherCourse){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(VIEW_NAME);
                if(teacherCourse!=null){
                    if(teacherCourse.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getCoid())){
                        WHERE("coid=#{coid}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getTid())){
                        WHERE("tid=#{tid}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getCid())){
                        WHERE("cid=#{cid}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getSection())){
                        WHERE("section=#{section}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getTerm())){
                        WHERE("term=#{term}");
                    }
                    if(StringUtil.isNotEmpty(teacherCourse.getWeek())){
                        WHERE("week=#{week}");
                    }
                    if(teacherCourse.getType()!=null){
                        WHERE("type=#{type}");
                    }
                    if(teacherCourse.getStatus()!=null){
                        WHERE("status=#{status}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(TeacherCourse teacherCourse){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(teacherCourse.getCoid())){
                    SET("coid=#{coid}");
                }
                if(StringUtil.isNotEmpty(teacherCourse.getTid())){
                    SET("tid=#{tid}");
                }
                if(StringUtil.isNotEmpty(teacherCourse.getCid())){
                    SET("cid=#{cid}");
                }
                if(StringUtil.isNotEmpty(teacherCourse.getSection())){
                    SET("section=#{section}");
                }
                if(StringUtil.isNotEmpty(teacherCourse.getTerm())){
                    SET("term=#{term}");
                }
                if(StringUtil.isNotEmpty(teacherCourse.getWeek())){
                    SET("week=#{week}");
                }
                if(teacherCourse.getStatus()!=null){
                    SET("status=#{status}");
                }
                if(teacherCourse.getType()!=null){
                    SET("type=#{type}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
