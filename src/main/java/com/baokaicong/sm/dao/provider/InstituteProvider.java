package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Clazz;
import com.baokaicong.sm.bean.entity.Institute;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class InstituteProvider {
    private static final String TABLE_NAME="t_institute";
    public String queryAll(Institute institute){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(institute!=null){
                    if(institute.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(institute.getIid())){
                        WHERE("iid=#{iid}");
                    }
                    if(StringUtil.isNotEmpty(institute.getName())){
                        WHERE("name=#{name}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(Institute institute){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(institute.getIid())){
                    SET("iid=#{iid}");
                }
                if(StringUtil.isNotEmpty(institute.getName())){
                    SET("name=#{name}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
