package com.baokaicong.sm.dao.provider;

import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

public class ScoreProvider {
    private static final String TABLE_NAME="t_score";
    private static final String VIEW_NAME="v_score";
    public String queryAll(Score score){
        SQL sql=new SQL(){
            {
                SELECT("*");
                FROM(VIEW_NAME);
                if(score!=null){
                    if(score.getId()!=null){
                        WHERE("id=#{id}");
                    }
                    if(StringUtil.isNotEmpty(score.getCoid())){
                        WHERE("coid=#{coid}");
                    }
                    if(StringUtil.isNotEmpty(score.getSid())){
                        WHERE("sid=#{sid}");
                    }
                    if(score.getCredit()!=null){
                        WHERE("credit=#{credit}");
                    }
                    if(score.getScore()!=null){
                        WHERE("score=#{score}");
                    }
                    if(score.getType()!=null){
                        WHERE("type=#{type}");
                    }
                    if(StringUtil.isNotEmpty(score.getTerm())){
                        WHERE("term=#{term}");
                    }
                    if(StringUtil.isNotEmpty(score.getWeek())){
                        WHERE("week=#{week}");
                    }
                    if(StringUtil.isNotEmpty(score.getSection())){
                        WHERE("section=#{section}");
                    }
                    if(StringUtil.isNotEmpty(score.getTid())){
                        WHERE("tid=#{tid}");
                    }
                    if(StringUtil.isNotEmpty(score.getCid())){
                        WHERE("cid=#{cid}");
                    }
                    if(score.getStatus()!=null){
                        WHERE("status=#{status}");
                    }
                }
            }
        };
        return sql.toString();
    }

    public String update(Score score){
        return new SQL(){
            {
                UPDATE(TABLE_NAME);
                if(StringUtil.isNotEmpty(score.getCoid())){
                    SET("coid=#{coid}");
                }
                if(StringUtil.isNotEmpty(score.getSid())){
                    SET("sid=#{sid}");
                }
                if(score.getCredit()!=null){
                    SET("credit=#{credit}");
                }
                if(score.getScore()!=null){
                    SET("score=#{score}");
                }
                if(StringUtil.isNotEmpty(score.getTid())){
                    SET("tid=#{tid}");
                }
                if(score.getStatus()!=null){
                    SET("status=#{status}");
                }
                if(StringUtil.isNotEmpty(score.getTerm())){
                    SET("term=#{term}");
                }
                if(StringUtil.isNotEmpty(score.getWeek())){
                    SET("week=#{week}");
                }
                if(StringUtil.isNotEmpty(score.getSection())){
                    SET("section=#{section}");
                }
                if(score.getType()!=null){
                    SET("type=#{type}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

}
