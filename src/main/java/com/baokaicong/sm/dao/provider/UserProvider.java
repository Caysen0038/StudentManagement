package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Student;
import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private static final String TABLE_NAME="t_user";
    private static final String VIEW_NAME="v_user";
    public String queryAll(User user){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(VIEW_NAME);
                    if(user!=null){
                        if(user.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(user.getUid())){
                            WHERE("uid=#{uid}");
                        }
                        if(StringUtil.isNotEmpty(user.getUsername())){
                            WHERE("username=#{username}");
                        }
                        if(StringUtil.isNotEmpty(user.getPassword())){
                            WHERE("password=#{password}");
                        }
                        if(StringUtil.isNotEmpty(user.getTime())){
                            WHERE("time=#{time}");
                        }
                        if(StringUtil.isNotEmpty(user.getToken())){
                            WHERE("token=#{token}");
                        }
                        if(StringUtil.isNotEmpty(user.getAid())){
                            WHERE("aid=#{aid}");
                        }
                        if(StringUtil.isNotEmpty(user.getRid())){
                            WHERE("rid=#{rid}");
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

    public String update(User user){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(user.getUid())){
                    SET("uid=#{uid}");
                }
                if(StringUtil.isNotEmpty(user.getUsername())){
                    SET("username=#{username}");
                }
                if(StringUtil.isNotEmpty(user.getPassword())){
                    SET("password=#{password}");
                }
                if(StringUtil.isNotEmpty(user.getTime())){
                    SET("time=#{time}");
                }
                if(StringUtil.isNotEmpty(user.getToken())){
                    SET("token=#{token}");
                }
                if(StringUtil.isNotEmpty(user.getAid())){
                    SET("aid=#{aid}");
                }
                if(StringUtil.isNotEmpty(user.getRid())){
                    SET("rid=#{rid}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
