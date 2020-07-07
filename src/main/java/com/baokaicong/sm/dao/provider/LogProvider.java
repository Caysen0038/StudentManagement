package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Log;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class LogProvider {
    private static final String TABLE_NAME="t_log";
    public String queryAll(Log log){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(TABLE_NAME);
                    if(log!=null){
                        if(log.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(log.getInfo())){
                            WHERE("info=#{info}");
                        }
                        if(log.getLevel()!=null){
                            WHERE("level=#{level}");
                        }
                        if(StringUtil.isNotEmpty(log.getTime())){
                            WHERE("time=#{time}");
                        }
                        if(StringUtil.isNotEmpty(log.getUid())){
                            WHERE("uid=#{uid}");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "select * from t_log";
    }

    public String update(Log log){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(log.getUid())){
                    SET("uid=#{uid}");
                }
                if(StringUtil.isNotEmpty(log.getInfo())){
                    SET("info=#{info}");
                }
                if(log.getLevel()!=null){
                    SET("level=#{level}");
                }
                if(StringUtil.isNotEmpty(log.getTime())){
                    SET("time=#{time}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
