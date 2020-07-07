package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class CourseProvider {
    private static final String TABLE_NAME="t_course";
    private static final String VIEW_NAME="v_course";
    public String queryAll(Course course){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(course!=null){
                    if(course.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(course.getCoid())){
                        WHERE("coid=#{coid}");
                    }
                    if(StringUtil.isNotEmpty(course.getName())){
                        WHERE("name=#{name}");
                    }
                    if(course.getCredit()!=null){
                        WHERE("credit=#{credit}");
                    }
                    if(course.getType()!=null){
                        WHERE("type=#{type}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(Course course){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(course.getCoid())){
                    SET("coid=#{coid}");
                }
                if(StringUtil.isNotEmpty(course.getName())){
                    SET("name=#{name}");
                }
                if(course.getCredit()!=null){
                    SET("credit=#{credit}");
                }
                if(course.getType()!=null){
                    SET("type=#{type}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
