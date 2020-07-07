package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Status;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Mapper
@Repository
public interface StatusDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_status" +
            "        where id = #{id}")
    Status queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_status" +
            "        limit #{offset}, #{limit}")
    List<Status> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param status 实例对象
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_status")
    List<Status> queryAll(Status status);

    /**
     * 新增数据
     *
     * @param status 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_status(code, value)" +
            "        values (#{code}, #{value})")
    int insert(Status status);

    /**
     * 修改数据
     *
     * @param status 实例对象
     * @return 影响行数
     */
    @Update("update sm.t_status"+
            "        where id = #{id}")
    int update(Status status);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Update("delete from sm.t_status where id = #{id}")
    int deleteById(Integer id);

}