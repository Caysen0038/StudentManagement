package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Student;
import com.baokaicong.sm.dao.provider.StudentProvider;
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
public interface StudentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.v_student" +
            "        where id = #{id}")
    Student queryById(Integer id);

    @Select("select * from v_student where sid=#{sid} limit 0,1")
    Student queryBySid(String sid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.v_student" +
            "        limit #{offset}, #{limit}")
    List<Student> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param student 实例对象
     * @return 对象列表
     */
    @SelectProvider(type = StudentProvider.class,method = "queryAll")
    List<Student> queryAll(Student student);

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_student(sid, name, sex, cid, iid,grade)\n" +
            "        values (#{sid}, #{name}, #{sex}, #{cid}, #{iid},#{grade})")
    int insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = StudentProvider.class,method = "update")
    int update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_student where id = #{id}")
    int deleteById(Integer id);

}