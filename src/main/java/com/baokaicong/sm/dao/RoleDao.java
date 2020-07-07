package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Role;
import com.baokaicong.sm.dao.provider.RoleProvider;
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
public interface RoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_role" +
            "        where id = #{id}")
    Role queryById(Integer id);

    /**
     * 通过rid查询
     * @param rid
     * @return
     */
    @Select("select * from t_role where rid=#{id} limit 0,1")
    Role queryByRid(String rid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_role" +
            "        limit #{offset}, #{limit}")
    List<Role> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param role 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = RoleProvider.class,method = "queryAll")
    List<Role> queryAll(Role role);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_role(rid, name)" +
            "        values (#{rid}, #{name})")
    int insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = RoleProvider.class,method = "update")
    int update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_role where id = #{id}")
    int deleteById(Integer id);

}