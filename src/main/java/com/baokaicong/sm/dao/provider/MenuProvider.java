package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Menu;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class MenuProvider {
    private static final String TABLE_NAME="t_menu";
    private static final String VIEW_NAME="v_menu";
    public String queryAll(Menu menu){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(VIEW_NAME);
                    if(menu!=null){
                        if(menu.getId()!=null){
                            WHERE("id=#{id}");
                        }
                        if(StringUtil.isNotEmpty(menu.getMid())){
                            WHERE("mid=#{mid}");
                        }
                        if(StringUtil.isNotEmpty(menu.getIcon())){
                            WHERE("icon=#{icon}");
                        }
                        if(StringUtil.isNotEmpty(menu.getAuid())){
                            WHERE("auid=#{auid}");
                        }
                        if(StringUtil.isNotEmpty(menu.getName())){
                            WHERE("name=#{name}");
                        }
                        if(StringUtil.isNotEmpty(menu.getParentMid())){
                            WHERE("parent_mid=#{parentMid}");
                        }
                        if(StringUtil.isNotEmpty(menu.getTarget())){
                            WHERE("target=#{target}");
                        }
                        if(StringUtil.isNotEmpty(menu.getType())){
                            WHERE("type=#{type}");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "select * from t_menu";
    }

    public String update(Menu menu){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(menu.getMid())){
                    SET("mid=#{mid}");
                }
                if(StringUtil.isNotEmpty(menu.getIcon())){
                    SET("icon=#{icon}");
                }
                if(StringUtil.isNotEmpty(menu.getAuid())){
                    SET("auid=#{auid}");
                }
                if(StringUtil.isNotEmpty(menu.getName())){
                    SET("name=#{name}");
                }
                if(StringUtil.isNotEmpty(menu.getParentMid())){
                    SET("parent_mid=#{parentMid}");
                }
                if(StringUtil.isNotEmpty(menu.getTarget())){
                    SET("target=#{target}");
                }
                if(StringUtil.isNotEmpty(menu.getType())){
                    SET("type=#{type}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    public String listByAuth(List<String> list){
        try{
            SQL sql=new SQL(){
                {
                    SELECT("*");
                    FROM(VIEW_NAME);
                    if(list!=null){
                        StringBuilder sb=new StringBuilder();
                        for(String str:list){
                            sb.append("sb").append(",");
                        }
                        if(sb.length()>1){
                            String auth=sb.substring(0,sb.length()-1);
                            WHERE(" auid in ("+auth+")");
                        }
                    }
                }
            };
            return sql.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
