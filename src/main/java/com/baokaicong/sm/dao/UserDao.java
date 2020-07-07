package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.User;
import com.baokaicong.sm.dao.provider.UserProvider;
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
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_user" +
            "        where id = #{id}")
    User queryById(Integer id);

    /**
     * 通过uid查询单条数据
     * @param uid
     * @return
     */
    @Select("select *"+
            "        from sm.v_user" +
            "        where uid = #{uid}" +
            "        limit 0,1")
    User queryByUid(String uid);

    /**
     * 通过username查询单条数据
     * @param username
     * @return
     */
    @Select("select *"+
            "        from sm.v_user" +
            "        where username = #{username}" +
            "        limit 0,1")
    User queryByUsername(String username);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.v_user" +
            "        limit #{offset}, #{limit}")
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = UserProvider.class,method = "queryAll")
    List<User> queryAll(User user);


    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_user(uid, username, password, time, token, aid,rid)" +
            "        values (#{uid}, #{username}, #{password}, #{time}, #{token}, #{aid},#{rid})")
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = UserProvider.class,method = "update")
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_user where id = #{id}")
    int deleteById(Integer id);

}