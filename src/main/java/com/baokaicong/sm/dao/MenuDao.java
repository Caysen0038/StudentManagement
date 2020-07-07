package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Menu;
import com.baokaicong.sm.dao.provider.MenuProvider;
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
public interface MenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.v_menu" +
            "        where id = #{id}")
    Menu queryById(Integer id);

    @Select("select * from v_menu where mid=#{mid} limit 0,1")
    Menu queryByMid(String mid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.v_menu" +
            "        limit #{offset}, #{limit}")
    List<Menu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = MenuProvider.class,method = "queryAll")
    List<Menu> queryAll(Menu menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_menu(mid, name, target, icon, parent_mid, type, auid)\n" +
            "        values (#{mid}, #{name}, #{target}, #{icon}, #{parentMid}, #{type}, #{auid})")
    int insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = MenuProvider.class,method="update")
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_menu where id = #{id}")
    int deleteById(Integer id);

}