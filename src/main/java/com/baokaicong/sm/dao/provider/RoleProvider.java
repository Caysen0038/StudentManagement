package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Role;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class RoleProvider {
    private static final String TABLE_NAME="t_role";
    public String queryAll(Role role){
        return new SQL(){
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if(role!=null){
                    if(role.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(role.getRid())){
                        WHERE("rid=#{rid}");
                    }
                    if(StringUtil.isNotEmpty(role.getName())){
                        WHERE("name=#{name}");
                    }
                }
            }
        }.toString();
    }

    public String update(Role role){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(role.getRid())){
                    SET("rid=#{rid}");
                }
                if(StringUtil.isNotEmpty(role.getName())){
                    SET("name=#{name}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
