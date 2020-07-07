package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Property;
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
public interface PropertyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_property" +
            "        where id = #{id}")
    Property queryById(Integer id);

    @Select("select * from sm.t_property where name=#{name}")
    Property queryByName(String name);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_property" +
            "        limit #{offset}, #{limit}")
    List<Property> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param property 实例对象
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_property")
    List<Property> queryAll(Property property);

    /**
     * 新增数据
     *
     * @param property 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_property(name, value)" +
            "        values (#{name}, #{value})")
    int insert(Property property);

    /**
     * 修改数据
     *
     * @param property 实例对象
     * @return 影响行数
     */
    @Update("update sm.t_property" +
            " set value=#{value}       where id = #{id}")
    int update(Property property);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_property where id = #{id}")
    int deleteById(Integer id);

}