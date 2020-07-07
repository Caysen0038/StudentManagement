package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Log;
import com.baokaicong.sm.dao.provider.LogProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 22:11:13
 */
@Mapper
@Repository
public interface LogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_log" +
            "        where id = #{id}")
    Log queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_log" +
            "        limit #{offset}, #{limit}")
    List<Log> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param log 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = LogProvider.class,method = "queryAll")
    List<Log> queryAll(Log log);

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_log(level, info, uid, time)" +
            "        values (#{level}, #{info}, #{uid}, #{time})")
    int insert(Log log);

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = LogProvider.class,method = "update")
    int update(Log log);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_log where id = #{id}")
    int deleteById(Long id);

}