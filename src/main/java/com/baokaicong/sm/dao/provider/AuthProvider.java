package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class AuthProvider {
    private static final String TABLE_NAME="t_auth";
    private static final String VIEW_NAME="v_auth";
    public String queryAll(Auth auth){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(TABLE_NAME);
                    if(auth!=null){
                        if(auth.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(auth.getAuid())){
                            WHERE("auid=#{auid}");
                        }
                        if(StringUtil.isNotEmpty(auth.getName())){
                            WHERE("name=#{name}");
                        }
                        if(StringUtil.isNotEmpty(auth.getToken())){
                            WHERE("token=#{token}");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "select * from t_auth";
    }

    public String update(Auth auth){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(auth.getAuid())){
                    SET("auid=#{auid}");
                }
                if(StringUtil.isNotEmpty(auth.getName())){
                    SET("name=#{name}");
                }
                if(StringUtil.isNotEmpty(auth.getToken())){
                    SET("token=#{token}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
