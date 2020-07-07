package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.RoleAuth;
import com.baokaicong.sm.dao.provider.RoleAuthProvider;
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
public interface RoleAuthDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_role_auth\n" +
            "        where id = #{id}")
    RoleAuth queryById(Integer id);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_role_auth\n" +
            "        limit #{offset}, #{limit}")
    List<RoleAuth> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param roleAuth 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = RoleAuthProvider.class,method = "queryAll")
    List<RoleAuth> queryAll(RoleAuth roleAuth);

    /**
     * 新增数据
     *
     * @param roleAuth 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_role_auth(rid, auid, time)\n" +
            "        values (#{rid}, #{auid}, #{time})")
    int insert(RoleAuth roleAuth);

    /**
     * 修改数据
     *
     * @param roleAuth 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = RoleAuthProvider.class,method = "update")
    int update(RoleAuth roleAuth);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_role_auth where id = #{id}")
    int deleteById(Integer id);

}