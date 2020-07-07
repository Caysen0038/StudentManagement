package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.dao.provider.AuthProvider;
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
public interface AuthDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_auth" +
            "        where id = #{id}")
    Auth queryById(Integer id);

    /**
     * 通过Auid查询单条数据
     * @param auid
     * @return
     */
    @Select("select *"+
            "        from sm.t_auth" +
            "        where auid = #{auid}" +
            "        limit 0,1")
    Auth queryByAuid(String auid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_auth" +
            "        limit #{offset}, #{limit}")
    List<Auth> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param auth 实例对象
     * @return 对象列表
     */
    @SelectProvider(type= AuthProvider.class,method = "queryAll")
    List<Auth> queryAll(Auth auth);

    /**
     * 新增数据
     *
     * @param auth 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_auth(auid, name, token)\n" +
            "        values (#{auid}, #{name}, #{token})")
    int insert(Auth auth);

    /**
     * 修改数据
     *
     * @param auth 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = AuthProvider.class,method = "update")
    int update(Auth auth);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_auth where id = #{id}")
    int deleteById(Integer id);

}