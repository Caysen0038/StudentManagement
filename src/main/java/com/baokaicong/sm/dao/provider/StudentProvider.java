package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Student;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class StudentProvider {
    private static final String TABLE_NAME="t_student";
    private static final String VIEW_NAME="v_student";
    public String queryAll(Student student){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(VIEW_NAME);
                    if(student!=null){
                        if(student.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(student.getSid())){
                            WHERE("sid=#{sid}");
                        }
                        if(StringUtil.isNotEmpty(student.getName())){
                            WHERE("name=#{name}");
                        }
                        if(StringUtil.isNotEmpty(student.getSex())){
                            WHERE("sex=#{sex}");
                        }
                        if(StringUtil.isNotEmpty(student.getCid())){
                            WHERE("cid=#{cid}");
                        }
                        if(StringUtil.isNotEmpty(student.getIid())){
                            WHERE("iid=#{iid}");
                        }
                        if(StringUtil.isNotEmpty(student.getGrade())){
                            WHERE("grade=#{grade}");
                        }
                        if(StringUtil.isNotEmpty(student.getMobile())){
                            WHERE("mobile=#{mobile}");
                        }
                        if(StringUtil.isNotEmpty(student.getImg())){
                            WHERE("img=#{img}");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "select * from t_student";
    }

    public String update(Student student){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(student.getSid())){
                    SET("sid=#{sid}");
                }
                if(StringUtil.isNotEmpty(student.getName())){
                    SET("name=#{name}");
                }
                if(StringUtil.isNotEmpty(student.getSex())){
                    SET("sex=#{sex}");
                }
                if(StringUtil.isNotEmpty(student.getCid())){
                    SET("cid=#{cid}");
                }
                if(StringUtil.isNotEmpty(student.getIid())){
                    SET("iid=#{iid}");
                }
                if(StringUtil.isNotEmpty(student.getName())){
                    SET("grade=#{grade}");
                }
                if(StringUtil.isNotEmpty(student.getMobile())){
                    SET("mobile=#{mobile}");
                }
                if(StringUtil.isNotEmpty(student.getImg())){
                    SET("img=#{img}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
