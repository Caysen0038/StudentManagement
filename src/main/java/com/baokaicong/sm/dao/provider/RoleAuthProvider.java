package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.RoleAuth;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class RoleAuthProvider {
    private static final String TABLE_NAME="t_role_auth";
    public String queryAll(RoleAuth roleAuth){
        return new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(roleAuth!=null){
                    if(roleAuth.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(roleAuth.getRid())){
                        WHERE("rid=#{rid}");
                    }
                    if(StringUtil.isNotEmpty(roleAuth.getAuid())){
                        WHERE("auid=#{auid}");
                    }
                    if(StringUtil.isNotEmpty(roleAuth.getTime())){
                        WHERE("time=#{time}");
                    }
                }
            }
        }.toString();
    }

    public String update(RoleAuth roleAuth){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(roleAuth.getRid())){
                    SET("rid=#{rid}");
                }
                if(StringUtil.isNotEmpty(roleAuth.getAuid())){
                    SET("auid=#{auid}");
                }
                if(StringUtil.isNotEmpty(roleAuth.getTime())){
                    SET("time=#{time}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
