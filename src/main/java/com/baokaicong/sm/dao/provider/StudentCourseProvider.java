package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.StudentCourse;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class StudentCourseProvider {
    private static final String TABLE_NAME="t_student_course";
    private static final String VIEW_NAME="v_student_course";
    public String queryAll(StudentCourse studentCourse){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(studentCourse!=null){
                    if(studentCourse.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(studentCourse.getCoid())){
                        WHERE("coid=#{coid}");
                    }
                    if(StringUtil.isNotEmpty(studentCourse.getSid())){
                        WHERE("sid=#{sid}");
                    }
                    if(StringUtil.isNotEmpty(studentCourse.getTime())){
                        WHERE("time=#{time}");
                    }
                    if(studentCourse.getStatus()!=null){
                        WHERE("status=#{status}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(StudentCourse studentCourse){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(studentCourse.getCoid())){
                    SET("coid=#{coid}");
                }
                if(StringUtil.isNotEmpty(studentCourse.getSid())){
                    SET("sid=#{sid}");
                }
                if(StringUtil.isNotEmpty(studentCourse.getTime())){
                    SET("time=#{time}");
                }
                if(studentCourse.getStatus()!=null){
                    SET("status=#{status}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
