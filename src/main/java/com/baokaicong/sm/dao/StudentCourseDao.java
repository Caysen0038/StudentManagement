package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.StudentCourse;
import com.baokaicong.sm.dao.provider.AuthProvider;
import com.baokaicong.sm.dao.provider.StudentCourseProvider;
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
public interface StudentCourseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_student_course" +
            "        where id = #{id}")
    StudentCourse queryById(Long id);

    /**
     * 通过sid查询单条数据
     * @param sid
     * @return
     */
    @Select("select *"+
            "        from sm.t_student_course" +
            "        where sid = #{sid}" +
            "        limit 0,1")
    StudentCourse queryBySid(String sid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_student_course" +
            "        limit #{offset}, #{limit}")
    List<StudentCourse> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param studentCourse 实例对象
     * @return 对象列表
     */
    @SelectProvider(type= StudentCourseProvider.class,method = "queryAll")
    List<StudentCourse> queryAll(StudentCourse studentCourse);

    /**
     * 新增数据
     *
     * @param studentCourse 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_student_course(sid, coid, time,status)\n" +
            "        values (#{sid}, #{coid}, #{time},#{status})")
    int insert(StudentCourse studentCourse);

    /**
     * 修改数据
     *
     * @param studentCourse 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = StudentCourseProvider.class,method = "update")
    int update(StudentCourse studentCourse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_student_course where id = #{id}")
    int deleteById(Long id);

}