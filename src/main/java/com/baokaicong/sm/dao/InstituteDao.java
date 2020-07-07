package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Institute;
import com.baokaicong.sm.dao.provider.InstituteProvider;
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
public interface InstituteDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_institute" +
            "        where id = #{id}")
    Institute queryById(Integer id);

    @Select("select * from t_institute where iid=#{iid} limit 0,1")
    Institute queryByIid(String iid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_institute" +
            "        limit #{offset}, #{limit}")
    List<Institute> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param institute 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = InstituteProvider.class,method = "queryAll")
    List<Institute> queryAll(Institute institute);

    /**
     * 新增数据
     *
     * @param institute 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_institute(iid, name)\n" +
            "        values (#{iid}, #{name})")
    int insert(Institute institute);

    /**
     * 修改数据
     *
     * @param institute 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = InstituteProvider.class,method = "update")
    int update(Institute institute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_institute where id = #{id}")
    int deleteById(Integer id);

}