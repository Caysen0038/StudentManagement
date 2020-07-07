package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Teacher;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class TeacherProvider {
    private static final String TABLE_NAME="t_teacher";
    private static final String VIEW_NAME="v_teacher";
    public String queryAll(Teacher teacher){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(VIEW_NAME);
                    if(teacher!=null){
                        if(teacher.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getTid())){
                            WHERE("tid=#{tid}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getName())){
                            WHERE("name=#{name}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getSex())){
                            WHERE("sex=#{sex}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getIid())){
                            WHERE("iid=#{iid}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getMobile())){
                            WHERE("mobile=#{mobile}");
                        }
                        if(StringUtil.isNotEmpty(teacher.getImg())){
                            WHERE("img=#{img}");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "select * from t_teacher";
    }

    public String update(Teacher teacher){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(teacher.getTid())){
                    SET("tid=#{tid}");
                }
                if(StringUtil.isNotEmpty(teacher.getName())){
                    SET("name=#{name}");
                }
                if(StringUtil.isNotEmpty(teacher.getSex())){
                    SET("sex=#{sex}");
                }
                if(StringUtil.isNotEmpty(teacher.getIid())){
                    SET("iid=#{iid}");
                }
                if(StringUtil.isNotEmpty(teacher.getMobile())){
                    SET("mobile=#{mobile}");
                }
                if(StringUtil.isNotEmpty(teacher.getImg())){
                    SET("img=#{img}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
