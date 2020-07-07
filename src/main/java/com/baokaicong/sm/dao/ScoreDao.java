package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.dao.provider.CourseProvider;
import com.baokaicong.sm.dao.provider.ScoreProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:12
 */
@Mapper
@Repository
public interface ScoreDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_score" +
            "        where id = #{id}")
    Score queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_score" +
            "        limit #{offset}, #{limit}")
    List<Score> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param score 实例对象
     * @return 对象列表
     */
    @SelectProvider(type= ScoreProvider.class,method = "queryAll")
    List<Score> queryAll(Score score);

    /**
     * 新增数据
     *
     * @param score 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_score(sid, coid, score,tid,credit,status,term,week,section,type)" +
            "        values (#{sid}, #{coid}, #{score},#{tid},#{credit},#{status},#{term}," +
            "#{week},#{section},#{type})")
    int insert(Score score);

    /**
     * 修改数据
     *
     * @param score 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = ScoreProvider.class,method = "update")
    int update(Score score);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_score where id = #{id}")
    int deleteById(Long id);

}