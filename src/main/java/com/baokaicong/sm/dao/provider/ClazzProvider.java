package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Clazz;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class ClazzProvider {
    private static final String TABLE_NAME="t_clazz";
    private static final String VIEW_NAME="v_clazz";
    public String queryAll(Clazz clazz){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(VIEW_NAME);
                if(clazz!=null){
                    if(clazz.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(clazz.getCid())){
                        WHERE("cid=#{cid}");
                    }
                    if(StringUtil.isNotEmpty(clazz.getName())){
                        WHERE("name=#{name}");
                    }
                    if(StringUtil.isNotEmpty(clazz.getIid())){
                        WHERE("iid=#{iid}");
                    }
                    if(StringUtil.isNotEmpty(clazz.getGrade())){
                        WHERE("grade=#{grade}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(Clazz clazz){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(clazz.getCid())){
                    SET("cid=#{cid}");
                }
                if(StringUtil.isNotEmpty(clazz.getName())){
                    SET("name=#{name}");
                }
                if(StringUtil.isNotEmpty(clazz.getIid())){
                    SET("iid=#{iid}");
                }
                if(StringUtil.isNotEmpty(clazz.getGrade())){
                    SET("grade=#{grade}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
