package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Clazz;
import com.baokaicong.sm.dao.provider.ClazzProvider;
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
public interface ClazzDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.v_clazz" +
            "        where id = #{id}")
    Clazz queryById(Integer id);

    /**
     * 通过cid查询单条数据
     * @param cid
     * @return
     */
    @Select("select * from v_clazz where cid=#{cid} limit 0,1")
    Clazz queryByCid(String cid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select * from sm.v_clazz limit #{offset}, #{limit}")
    List<Clazz> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param clazz 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = ClazzProvider.class,method = "queryAll")
    List<Clazz> queryAll(Clazz clazz);

    /**
     * 新增数据
     *
     * @param clazz 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_clazz(cid, name, iid,grade)" +
            "        values (#{cid}, #{name}, #{iid},#{grade})")
    int insert(Clazz clazz);

    /**
     * 修改数据
     *
     * @param clazz 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = ClazzProvider.class,method = "update")
    int update(Clazz clazz);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_clazz where id = #{id}")
    int deleteById(Integer id);

}